package org.yugh.gatewaynew.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.globalauth.annotation.PreSkipAuth;
import org.yugh.globalauth.common.enums.ResultEnum;
import org.yugh.globalauth.util.ResultJson;

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
public class GatewayFallbackController {

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
    @RequestMapping({"/fallbackController"})
    public String fallBackController() {
        log.info("Gateway happening Exception ! ! !");
        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR).toString();
    }
}
