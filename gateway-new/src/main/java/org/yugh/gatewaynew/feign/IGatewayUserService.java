package org.yugh.gatewaynew.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yugh.common.pojo.dto.UserDTO;
import org.yugh.common.util.JsonResult;
import org.yugh.gatewaynew.feign.impl.GatewayUserServiceImpl;

/**
 * 同步用户
 *
 */
@FeignClient(value = "dataworks-usercenter-web", fallback = GatewayUserServiceImpl.class)
public interface IGatewayUserService {


    @RequestMapping(value = {"/user/syncUser"}, method = {RequestMethod.POST})
    JsonResult synUser(@RequestBody UserDTO userDto);
}
