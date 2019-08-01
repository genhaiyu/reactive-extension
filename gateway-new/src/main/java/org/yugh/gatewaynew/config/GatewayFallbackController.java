package org.yugh.gatewaynew.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.globalauth.common.enums.ResultEnum;
import org.yugh.globalauth.util.ResultJson;

/**
 * // 网关异常跳转
 *
 * @author 余根海
 * @creation 2019-07-12 14:34
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@RestController
public class GatewayFallbackController {

    @RequestMapping({"/fallbackController"})
    public String fallBackController() {
        log.info("网关发生致命错误! ! !");
        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR).toString();
    }
}
