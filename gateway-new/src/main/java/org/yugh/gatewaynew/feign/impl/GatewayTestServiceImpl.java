package org.yugh.gatewaynew.feign.impl;

import org.springframework.stereotype.Component;
import org.yugh.gatewaynew.feign.IGatewayTestService;
import org.yugh.globalauth.common.enums.HttpStatusEnum;
import org.yugh.globalauth.util.JsonResult;

/**
 * @author yugenhai
 */
@Component
public class GatewayTestServiceImpl implements IGatewayTestService {


    @Override
    public Object sendMsg1(Integer id, String name, String createdAtStart, String createdAtEnd, Integer pageIndex, Integer perPage) {
        return JsonResult.buildErrorResult(HttpStatusEnum.INTERNAL_SERVER_ERROR, "Feign调用失败");
    }
}
