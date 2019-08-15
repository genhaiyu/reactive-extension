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
import org.yugh.gatewaynew.context.GatewayContext;
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
 * This Component use gateway-core
 * And Webflux , since 5.0 reactive by HTTP, see reactor ${@link org.reactivestreams.Publisher}
 *
 * @author 余根海
 * @creation 2019-07-09 10:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
public class GatewayDataWorksFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthSkipUrlsProperties authSkipUrlsProperties;
    @Deprecated
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
        log.info("Current gateway session : {}", request.getId());
        if (blackServersCheck(context, exchange)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.BLACK_SERVER_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            return response.writeWith(Flux.just(buffer));
        }

        /**
         * if (!Arrays.stream(context.getPath().split(Constant.PREFIX)).anyMatch(white -> white.equals(authSkipUrlsProperties.getApiUrls()))) {
         */

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
            log.info("Current gateway session forward success : {}", request.getId());
            return chain.filter(mutableExchange);
        } else {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.WHITE_NOT_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            return response.writeWith(Flux.just(buffer));
        }
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
            boolean isLogin = authService.isLoginByReactive(request);
            if (isLogin) {
                try {
                    String ssoToken = authService.getUserTokenByGateway(request);
                    context.setSsoToken(ssoToken);
                } catch (Exception e) {
                    log.error("Feign user Server Exception : {}", e.getMessage());
                    context.setDoNext(false);
                    return;
                }
            } else {
                unLogin(context, request);
            }
        } catch (Exception e) {
            log.error("Invoke xx SSO RuntimeException :{}", e.getMessage());
            context.setDoNext(false);
        }
    }


    /**
     * 网关同步用户
     *
     * @param userDto
     */
    @Deprecated
    public void synUser(User userDto) {
        buildGatewayQueueThreadPool.execute(new Runnable() {
            @Override
            public void run() {
               // JsonResult user = gatewayUserService.synUser(userDto);
                //log.info("Feign user success : {}", user);
            }
        });

    }


    /**
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
        return "http://sso-stage.xx-corp.com";
    }


    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
