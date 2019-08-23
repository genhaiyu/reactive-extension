package org.yugh.gateway.controller;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.auth.annotation.PreSkipAuth;
import org.yugh.auth.common.enums.ResultEnum;
import org.yugh.auth.service.AuthService;
import org.yugh.auth.util.JsonResult;
import org.yugh.auth.util.ResultJson;
import reactor.core.publisher.Mono;

/**
 * Gateway default RuntimeException It's been here
 * <p>
 * see application-dev.yml fallbackUri
 *
 * @author 余根海
 * @creation 2019-07-12 14:34
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@PreSkipAuth
@RestController
public class GatewayController {


    @Autowired
    private AuthService authService;


    /**
     * Gateway default exception
     * <p>
     * It's configured in the see application-dev.yml fallbackUri
     * <p>
     * It's a must configuration
     * <p>
     * And it's no Http Type
     *
     * @return
     */
    @Synchronized
    @RequestMapping({"/fallbackController"})
    public String fallBackController() {
        log.info("Gateway happening Exception ! ! !");
        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR).toString();
    }


    /**
     * Gateway logout
     *
     * @param request
     * @param response
     * @return
     */
    @Synchronized
    @GetMapping("logout")
    public Mono logout(ServerHttpRequest request, ServerHttpResponse response) {
        try {
            authService.logoutByGateway(request, response);
            log.info("Logout Success!");
            return Mono.just(JsonResult.buildSuccessResult("Logout Success!"));
        } catch (Exception e) {
            log.info("Logout Failed!");
            return Mono.just(JsonResult.buildErrorResult("Logout Failed!"));
        }
    }
}
