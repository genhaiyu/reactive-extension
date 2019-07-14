package org.yugh.gateway.filter;

import com.netflix.hystrix.contrib.javanica.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.yugh.gateway.common.enums.ResultEnum;
import org.yugh.gateway.util.ResultJson;
import org.yugh.gatewaynew.config.GatewayContext;
import org.yugh.gatewaynew.feign.IUserService;
import org.yugh.gatewaynew.properties.AuthSkipUrlsProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;

/**
 * // 网关过滤器
 *
 * @author 余根海
 * @creation 2019-07-09 10:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
public class GatewayFilter implements GlobalFilter, Ordered {

    @Value("${spring.profiles.active}")
    private String activeType;
    @Autowired
    private AuthSkipUrlsProperties authSkipUrlsProperties;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    @Qualifier(value = "gatewaySynUserQueueThreadPool")
    private ExecutorService gatewaySynUserQueueThread;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserAuthUtil userAuthUtil;
    @Autowired
    private Config config;
    @Autowired
    private CookieUtils cookieUtils;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayContext context = new GatewayContext();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        if (blackServersCheck(context, exchange)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.BLACK_SERVER_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            return response.writeWith(Flux.just(buffer));
        }
        if (whiteListCheck(context, exchange)) {
            authToken(context, request);
            if (!context.isDoNext()) {
                byte[] failureInfo = ResultJson.failure(ResultEnum.LOGIN_ERROR_GATEWAY, context.getRedirectUrl()).toString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.writeWith(Flux.just(buffer));
            }
            ServerHttpRequest mutateReq = exchange.getRequest().mutate().header(Constants.DATAWORKS_GATEWAY_HEADERS, context.toString()).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutateReq).build();


            return chain.filter(mutableExchange);
        } else {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.WHITE_NOT_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            return response.writeWith(Flux.just(buffer));
        }
    }


    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    /**
     * 检查用户
     *
     * @param context
     * @param request
     * @return
     * @author yugenhai
     */
    private void authToken(GatewayContext context, ServerHttpRequest request) {
        try {
            boolean isLogin = sessionManager.isLogined(request);
            if (isLogin) {
                UserDo userDo = sessionManager.getUser(request);
                UserDto userDto = UserDto.builder().build();
                try {
                    Object redisUserToken = redisClient.get(userDo.getNo());
                    if (Objects.isNull(redisUserToken)) {
                        String token = userAuthUtil.generateToken(userDo.getNo(), userDo.getUserName(), userDo.getAliasName());
                        CommonUtils.copyProperties(userDo, userDto);
                        synUser(userDto);
                        redisClient.set(userDo.getNo(), token, 60 * 60L);
                        context.setGatewayToken(token);
                    } else {
                        CommonUtils.copyProperties(userDo, userDto);
                        synUser(userDto);
                    }
                } catch (Exception e) {
                    log.error("用户调用失败 : {}", e.getMessage());
                    context.setDoNext(false);
                    //FIXME 异步写入 ,并修改同步失败而不是未登录 by yugenhai
                    return;
                }
                context.setUser(userDo);
                context.setLogId(request.getId());
                context.setSsoToken(cookieUtils.getCookieByName(request, Constants.SESSION_GUAZI_TOKEN));
            } else {
                unLogin(context, request);
            }
        } catch (Exception e) {
            log.error("调用SSO获取用户信息异常 :{}", e.getMessage());
            context.setDoNext(false);
        }
    }


    /**
     * 网关同步用户
     * @param userDto
     */
    public void synUser(UserDto userDto){
        gatewaySynUserQueueThread.execute(new Runnable() {
            @Override
            public void run() {
                userService.synUser(userDto);
                log.info("用户同步成功 : {}", userDto);
            }
        });

    }



    /**
     * 未从guazi-corp.com登录SSO颁发token
     * 视为不能登录
     *
     * @param context
     * @param request
     */
    private void unLogin(GatewayContext context, ServerHttpRequest request) {
        String loginUrl = getSsoUrl(request) + "?returnUrl=" + request.getURI();
        context.setRedirectUrl(loginUrl);
        context.setDoNext(false);
        log.info("检查到该token对应的用户登录状态未登录  跳转到Login页面 : {} ", loginUrl);
    }


    /**
     * 白名单
     *
     * @param context
     * @param exchange
     * @return
     */
    private boolean whiteListCheck(GatewayContext context, ServerWebExchange exchange) {
        String url = exchange.getRequest().getURI().getPath();
        boolean white = authSkipUrlsProperties.getUrlPatterns().stream()
                .map(pattern -> pattern.matcher(url))
                .anyMatch(Matcher::find);
        if (white) {
            context.setPath(url);
            return true;
        }
        return false;
    }


    /**
     * 黑名单
     *
     * @param context
     * @param exchange
     * @return
     */
    private boolean blackServersCheck(GatewayContext context, ServerWebExchange exchange) {
        String instanceId = exchange.getRequest().getURI().getPath().substring(1, exchange.getRequest().getURI().getPath().indexOf('/', 1));
        if (!CollectionUtils.isEmpty(authSkipUrlsProperties.getDataWorksServers())) {
            boolean black = authSkipUrlsProperties.getServerPatterns().stream()
                    .map(pattern -> pattern.matcher(instanceId))
                    .anyMatch(Matcher::find);
            if (black) {
                context.setBlack(true);
                return true;
            }
        }
        return false;
    }



    /**
     * @param request
     * @return
     */
    private String getSsoUrl(ServerHttpRequest request) {
        String serverName = request.getPath().value();
        if (Objects.isNull(serverName)) {
            return config.getLogin_corp_url();
        }
        if (serverName.contains(Constants.DOMAIN_GUAZI_CLOUD)) {
            return config.getLogin_cloud_url();
        } else if (serverName.contains(Constants.DOMAIN_GUAZI_APPS)) {
            return config.getLogin_apps_url();
        } else if (serverName.contains(Constants.DOMAIN_GUAZI)) {
            return config.getLogin_url();
        } else {
            return config.getLogin_corp_url();
        }
    }




}
