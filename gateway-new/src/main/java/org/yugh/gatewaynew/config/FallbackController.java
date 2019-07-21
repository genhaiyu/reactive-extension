package org.yugh.gatewaynew.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yugh.common.common.enums.ResultEnum;
import org.yugh.common.util.ResultJson;
import org.yugh.gatewaynew.annotation.PreAuth;

/**
 * // 网关异常跳转
 *
 * @author 余根海
 * @creation 2019-07-12 14:34
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
public class FallbackController {

    @PreAuth("")
    @RequestMapping({"/fallbackController"})
    public String fallBackController() {
        log.info("路由异常请求 : {}");
        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR).toString();
    }
}
