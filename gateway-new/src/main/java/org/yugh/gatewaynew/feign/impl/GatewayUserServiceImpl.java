package org.yugh.gatewaynew.feign.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yugh.common.common.enums.HttpStatusEnum;
import org.yugh.common.pojo.dto.UserDTO;
import org.yugh.common.util.JsonResult;
import org.yugh.gatewaynew.feign.IGatewayUserService;

/**
 * 同步用户
 *
 */
@Slf4j
@Component
public class GatewayUserServiceImpl implements IGatewayUserService {


    @Override
    public JsonResult synUser(UserDTO userDto) {
        return JsonResult.buildErrorResult(HttpStatusEnum.INTERNAL_SERVER_ERROR, "调用失败");
    }


}
