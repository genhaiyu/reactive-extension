package org.yugh.gatewaynew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.globalauth.service.AuthService;
import org.yugh.globalauth.util.ResultJson;
import reactor.core.publisher.Mono;

/**
 * @author yugenhai
 */
@RestController
public class LogoutController {

    @Autowired
    private AuthService authService;

    @GetMapping("logout")
    public Mono<ResultJson> logout(ServerHttpRequest request, ServerHttpResponse response) {
        authService.logoutByGateway(request, response);
        return Mono.just(ResultJson.ok("退出成功"));
    }

}
