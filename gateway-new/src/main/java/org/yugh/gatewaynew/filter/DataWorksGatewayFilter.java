package org.yugh.gatewaynew.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.yugh.gatewaynew.config.GatewayContext;
import org.yugh.gatewaynew.properties.AuthSkipUrlsProperties;
import org.yugh.globalauth.common.constants.Constant;
import org.yugh.globalauth.common.enums.ResultEnum;
import org.yugh.globalauth.pojo.dto.User;
import org.yugh.globalauth.service.AuthService;
import org.yugh.globalauth.util.ResultJson;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
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

    @Autowired
    private AuthSkipUrlsProperties authSkipUrlsProperties;
    @Autowired
    @Qualifier(value = "gatewayQueueThreadPool")
    private ExecutorService buildGatewayQueueThreadPool;
    @Autowired
    private AuthService authService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayContext context = new GatewayContext();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        log.info("当前会话ID : {}", request.getId());
        //防止网关监控不到限流请求
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
            ServerHttpRequest mutateReq = exchange.getRequest().mutate().header(Constant.DATAWORKS_GATEWAY_HEADERS, context.getSsoToken()).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutateReq).build();
            log.info("当前会话转发成功 : {}", request.getId());
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
            // boolean isLogin = authService.isLoginByReactive(request);
            boolean isLogin = true;
            if (isLogin) {
                //User userDo = authService.getUserByReactive(request);
                try {
                    // String ssoToken = authCookieUtils.getCookieByNameByReactive(request, Constant.TOKEN);
                    String ssoToken = "123";
                    context.setSsoToken(ssoToken);
                } catch (Exception e) {
                    log.error("用户调用失败 : {}", e.getMessage());
                    context.setDoNext(false);
                    return;
                }
            } else {
                unLogin(context, request);
            }
        } catch (Exception e) {
            log.error("获取用户信息异常 :{}", e.getMessage());
            context.setDoNext(false);
        }
    }


    /**
     * 网关同步用户
     *
     * @param userDto
     */
    public void synUser(User userDto) {
        buildGatewayQueueThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                log.info("用户同步成功 : {}", "");
            }
        });

    }


    /**
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
        if (!CollectionUtils.isEmpty(authSkipUrlsProperties.getInstanceServers())) {
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
        return request.getPath().value();
    }

}
