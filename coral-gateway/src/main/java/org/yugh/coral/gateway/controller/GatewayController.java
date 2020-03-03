package org.yugh.coral.gateway.controller;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.coral.gateway.result.ResultEnum;
import org.yugh.coral.gateway.result.ResultJson;

/**
 * @author 余根海
 * @creation 2019-07-12 14:34
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
// @PreSkipAuth
@RestController
public class GatewayController {


//    private final AuthService authService;
//
//    @Autowired
//    private GatewayController(AuthService authService) {
//        this.authService = authService;
//    }


    @Synchronized
    @RequestMapping({"/fallbackController"})
    public String fallBackController() {
        log.info("Gateway happening Exception ! ! !");
        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR).toString();
    }


//    @Synchronized
//    @GetMapping("/logout")
//    public ResultJson logout(ServerHttpRequest request, ServerHttpResponse response) {
//        try {
//            boolean isLogout = authService.logoutByGateway(request, response);
//            if (isLogout) {
//                log.info("Logout Success!");
//                return ResultJson.ok("Logout Success!");
//            }
//        } catch (Exception e) {
//            log.info("Logout Failed!");
//        }
//        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR, "Logout Failed!");
//    }
}
