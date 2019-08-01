package org.yugh.gatewaynew.feign.impl;

import org.springframework.stereotype.Component;
import org.yugh.gatewaynew.feign.IGatewayTestService;
import org.yugh.globalauth.common.enums.ResultEnum;
import org.yugh.globalauth.util.ResultJson;

/**
 * @author yugenhai
 */
@Component
public class GatewayTestServiceImpl implements IGatewayTestService {

    @Override
    public Object sendMsg() {
        return ResultJson.failure(ResultEnum.FEIGN_ERROR, "Feign调用失败");
    }
}
