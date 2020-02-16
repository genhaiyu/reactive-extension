package org.yugh.coral.gateway.filter;

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
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.yugh.coral.core.common.constant.ClientMessageInfo;
import org.yugh.coral.core.common.constant.StringPool;
import org.yugh.coral.core.result.ResponseData;
import org.yugh.coral.core.result.ResultEnum;
import org.yugh.coral.core.result.ResultJson;
import org.yugh.coral.gateway.context.GatewayContext;
import org.yugh.coral.gateway.properties.AuthSkipUrlsProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;

/**
 * @author 余根海
 * @creation 2019-07-09 10:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
public class GatewayDataWorksFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthSkipUrlsProperties authSkipUrlsProperties;
//    @Autowired
//    private AuthService authService;
//    @Autowired
//    private AuthConfig authConfig;
//    @Autowired
//    private CacheProcessAdapter cacheProcessAdapter;
//    @Autowired
//    private JwtHelper jwtHelper;
    @Autowired(required = false)
    @Qualifier(value = "gatewayQueueThreadPool")
    private ExecutorService buildGatewayQueueThreadPool;


    /**
     * 失败的请求会格式 response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
     * 成功的请求会通过各个微服务自行转发的json格式
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayContext context = new GatewayContext();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if (blackServersCheck(context, exchange)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            ResponseData.build(()-> null);
            byte[] failureInfo = ResultJson.failure(ResultEnum.BLACK_SERVER_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            return response.writeWith(Flux.just(buffer));
        }
        if (whiteListCheck(context, exchange)) {
            authToken(context, request);
            if (!context.isDoNext()) {
                byte[] failureInfo = ResultJson.failure(ResultEnum.LOGIN_ERROR_GATEWAY, context.getRedirectUrl()).toString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                return response.writeWith(Flux.just(buffer));
            }
            ServerHttpRequest mutateReq = exchange.getRequest().mutate().header(ClientMessageInfo.AUTHORIZATION, context.getUserToken()).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutateReq).build();
            return chain.filter(mutableExchange);
        } else {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.WHITE_NOT_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            return response.writeWith(Flux.just(buffer));
        }
    }


    private void authToken(GatewayContext context, ServerHttpRequest request) {
        try {
            // boolean isLogin = "success";
            boolean isLogin = true;
            if (isLogin) {
                // String token = getToken;
                String token = "";
                context.setUserToken(token);
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
        Assert.notNull(authSkipUrlsProperties, () -> "AuthSkipUrlsProperties '" + authSkipUrlsProperties + "' is null");
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


    private boolean blackServersCheck(GatewayContext context, ServerWebExchange exchange) {
        //See whiteListCheck() is Check
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


    private String getSsoUrl() {
       // String env = "test";
        String env = "test/prod/dev/pre";
        Assert.hasText(env, "envSwitch is Empty");
        switch (env) {
            case StringPool
                    .TEST:
                return "/test";
            case StringPool
                    .PROD:
                return "/prod";
            default:
                return "pre";
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
