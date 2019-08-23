/*
 *  @Autowired
 *  @Qualifier(value = "gatewayQueueThreadPool")
 *  private ExecutorService buildGatewayQueueThreadPool;
 *  public void setRedisCache(User userDto) {
 *      buildGatewayQueueThreadPool.execute(new Runnable() {
 *   @Override
 *   public void run() {
 *      redisCache.sAdd(StringPool.DATAWORKS_USER_INFO, userDto.toString());
 * }
 * });}
 */
package org.yugh.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.yugh.auth.common.constants.Constant;
import org.yugh.auth.common.enums.ResultEnum;
import org.yugh.auth.config.AuthConfig;
import org.yugh.auth.pojo.dto.User;
import org.yugh.auth.service.AuthService;
import org.yugh.auth.util.ResultJson;
import org.yugh.auth.util.StringPool;
import org.yugh.gateway.cache.RedisCache;
import org.yugh.gateway.context.GatewayContext;
import org.yugh.gateway.properties.AuthSkipUrlsProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
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
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private RedisCache redisCache;


    /**
     * Core Filter
     * <p>
     * see {@link GlobalFilter}
     * <p>
     * Like this {@link org.springframework.cloud.gateway.filter.ForwardPathFilter}
     * <p>
     * Modify by 2019-08-21
     *
     * @param exchange
     * @param chain
     * @return
     * @author yugenhai
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayContext context = new GatewayContext();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        /**
         * if you request Instance Id to Gateway ,is failed
         * See {@link AuthSkipUrlsProperties}
         */
        if (blackServersCheck(context, exchange)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.BLACK_SERVER_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            return response.writeWith(Flux.just(buffer));
        }
        /**
         * Setting whiteLists
         * See yml --> api-urls:
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
            log.info("============> Current gateway session forward success : {}", request.getId());
            return chain.filter(mutableExchange);
        } else {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.WHITE_NOT_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            return response.writeWith(Flux.just(buffer));
        }
    }


    /**
     * Check SSO Return Token
     *
     * @param context
     * @param request
     * @return
     * @author yugenhai
     */
    private void authToken(GatewayContext context, ServerHttpRequest request) {
        try {
            boolean isLogin = authService.isLoginByReactive(request);
            log.info("Gateway Current State : {}", isLogin);
            if (isLogin) {
                try {
                    String ssoToken = authService.getUserTokenByGateway(request);
                    log.info("Gateway Current Token  : {}", ssoToken);
                    User user = authService.getUserByToken(ssoToken);
                    redisCache.sAdd(StringPool.DATAWORKS_USER_INFO, user.toString());
                    context.setSsoToken(ssoToken);
                } catch (Exception e) {
                    log.error("Set Redis For User Exception : {}", e.getMessage());
                    context.setDoNext(false);
                    return;
                }
            } else {
                unLogin(context);
            }
        } catch (Exception e) {
            log.error("Invoke SSO Exception :{}", e.getMessage());
            context.setDoNext(false);
        }
    }


    /**
     * + "?returnUrl=" + request.getURI()
     *
     * @param context
     * @author yugenhai
     */
    private void unLogin(GatewayContext context) {
        context.setRedirectUrl(getSsoUrl());
        context.setDoNext(false);
        log.info("Check Current Session is No Login, Return New Url Page : {} ", getSsoUrl());
    }


    /**
     * Check white
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
     * Request for Instance Id
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
     * Return New Url Page
     * <p>
     * {@link AuthConfig}
     *
     * @return
     */
    private String getSsoUrl() {
        String env = authConfig.getEnvSwitch();
        switch (env) {
            case StringPool
                    .TEST:
                return authConfig.getSsoTestUrl();
            case StringPool
                    .PROD:
                return authConfig.getSsoProdUrl();
            default:
                return authConfig.getSsoProdUrl();
        }
    }

    /**
     * Useful constant for the highest precedence value.
     * see {@link Integer MIN_VALUE}
     *
     * @return
     * @author yugenhai
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
