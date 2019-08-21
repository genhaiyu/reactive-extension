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
import org.yugh.auth.common.constants.Constant;
import org.yugh.auth.common.enums.ResultEnum;
import org.yugh.auth.pojo.dto.User;
import org.yugh.auth.service.AuthService;
import org.yugh.auth.util.JsonResult;
import org.yugh.auth.util.ResultJson;
import org.yugh.auth.util.SSOConfig;
import org.yugh.gatewaynew.context.GatewayContext;
import org.yugh.gatewaynew.feign.IGatewayUserService;
import org.yugh.gatewaynew.properties.AuthSkipUrlsProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
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
    private IGatewayUserService gatewayUserService;
    @Deprecated
    @Autowired
    @Qualifier(value = "gatewayQueueThreadPool")
    private ExecutorService buildGatewayQueueThreadPool;
    @Autowired
    private AuthService authService;
    @Autowired
    private SSOConfig ssoConfig;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayContext context = new GatewayContext();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        log.info("============> Current gateway session : {}", request.getId());
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
     * 检查用户
     *
     * @param context
     * @param request
     * @return
     * @author yugenhai
     */
    private void authToken(GatewayContext context, ServerHttpRequest request) {
        try {
            String ssoToken1 = authService.getUserTokenByGateway(request);

            log.info("网关获取到token ： {}", ssoToken1);

            boolean isLogin = authService.isLoginByReactive(request);
            log.info(" 网关当前登录状态 : {}", isLogin);
            if (isLogin) {
                try {
                    String ssoToken = authService.getUserTokenByGateway(request);
                    log.info(" 网关当前token : {}", ssoToken);
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
            log.error("Invoke SSO RuntimeException :{}", e.getMessage());
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
                JsonResult user = gatewayUserService.synUser(userDto);
                log.info("Feign user success : {}", user);
            }
        });

    }


    /**
     * + "?returnUrl=" + request.getURI()
     *
     * @param context
     * @param request
     */
    private void unLogin(GatewayContext context, ServerHttpRequest request) {
        String loginUrl = getSsoUrl(request);
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
     * {@link SSOConfig}
     *
     * @param request
     * @return
     */
    private String getSsoUrl(ServerHttpRequest request) {
        URI uri = request.getURI();
        String host = uri.getHost();
        if (ssoConfig.getTestSsoWeb().equals(host)) {
            return ssoConfig.getSsoTestUrl();
        } else if (ssoConfig.getTestStaffDev().equals(host)) {
            return ssoConfig.getSsoTestUrl();
        } else if (ssoConfig.getTestStageCorp().equals(host)) {
            return ssoConfig.getSsoTestUrl();
        } else {
            return ssoConfig.getSsoTestUrl();
        }

        /**if (ssoConfig.getProdSsoWeb().equals(host)) {
         return ssoConfig.getSsoProdUrl();
         } else if (ssoConfig.getProdSsoCorp().equals(host)) {
         return ssoConfig.getSsoProdUrl();
         } else if (ssoConfig.getProdStaff().equals(host)) {
         return ssoConfig.getSsoProdUrl();
         } else {
         return ssoConfig.getSsoProdUrl();
         }*/
    }


    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
