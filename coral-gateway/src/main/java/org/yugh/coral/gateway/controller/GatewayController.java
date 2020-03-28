package org.yugh.coral.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.coral.gateway.result.ResultEnum;
import org.yugh.coral.gateway.result.ResultJson;

/**
 * @author 余根海
 * @creation 2019-07-12 14:34
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
// @PreSkipAuth
@RestController
public class GatewayController {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayController.class);

//    private final AuthService authService;
//
//    @Autowired
//    private GatewayController(AuthService authService) {
//        this.authService = authService;
//    }


    @RequestMapping({"/fallbackController"})
    public String fallBackController() {
        LOG.info("Gateway happening Exception ! ! !");
        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR).toString();
    }


//    @GetMapping("/logout")
//    public ResultJson logout(ServerHttpRequest request, ServerHttpResponse response) {
//        try {
//            boolean isLogout = authService.logoutByGateway(request, response);
//            if (isLogout) {
//                LOG.info("Logout Success!");
//                return ResultJson.ok("Logout Success!");
//            }
//        } catch (Exception e) {
//            LOG.info("Logout Failed!");
//        }
//        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR, "Logout Failed!");
//    }
}
