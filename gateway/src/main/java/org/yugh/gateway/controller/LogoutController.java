package org.yugh.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.auth.annotation.PreSkipAuth;
import org.yugh.auth.service.AuthService;
import org.yugh.auth.util.JsonResult;
import reactor.core.publisher.Mono;

/**
 * @author yugenhai
 */
@Slf4j
@PreSkipAuth
@RestController
public class LogoutController {

    @Autowired
    private AuthService authService;

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
