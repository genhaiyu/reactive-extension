package org.yugh.gatewaynew.filter;

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
import org.yugh.common.auth.Config;
import org.yugh.common.auth.CookieUtils;
import org.yugh.common.auth.SessionManager;
import org.yugh.common.common.constants.Constants;
import org.yugh.common.common.enums.ResultEnum;
import org.yugh.common.config.RedisClient;
import org.yugh.common.model.User;
import org.yugh.common.pojo.dto.UserDTO;
import org.yugh.common.util.CommonUtils;
import org.yugh.common.util.JsonResult;
import org.yugh.common.util.ResultJson;
import org.yugh.common.util.jwt.UserAuthUtil;
import org.yugh.gatewaynew.config.GatewayContext;
import org.yugh.gatewaynew.feign.IGatewayUserService;
import org.yugh.gatewaynew.properties.AuthSkipUrlsProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;

/**
 * // 数据工厂网关服务
 *
 * @author 余根海
 * @creation 2019-07-09 10:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
public class DataWorksGatewayFilter implements GlobalFilter, Ordered {

    @Value("${spring.profiles.active}")
    private String activeType;
    @Autowired
    private AuthSkipUrlsProperties authSkipUrlsProperties;
    @Autowired
    private IGatewayUserService gatewayUserService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    @Qualifier(value = "gatewayQueueThreadPool")
    private ExecutorService buildGatewayQueueThreadPool;
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
        //防止网关监控不到请求
        if (blackServersCheck(context, exchange)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.BLACK_SERVER_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            return response.writeWith(Flux.just(buffer));
        }
        //白名单
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
            //黑名单
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
                User userDo = sessionManager.getUser(request);
                UserDTO userDto = UserDTO.builder().build();
                try {
                    //FIXME 修改
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
                context.setUserNo(userDo.getNo());
                context.setLogId(request.getId());
                context.setSsoToken(cookieUtils.getCookieByName(request, Constants.TOKEN));
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
     *
     * @param userDto
     */
    public void synUser(UserDTO userDto) {
        buildGatewayQueueThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                JsonResult user = gatewayUserService.synUser(userDto);
                log.info("用户同步成功 : {}", user);
            }
        });

    }


    /**
     * 未从SSO颁发token
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
        /**
         *  add code
         */
        return null;
    }

}
