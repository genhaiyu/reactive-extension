package org.yugh.gatewaynew.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.gatewaynew.feign.IGatewayTestService;

/**
 * @author yugenhai
 */
@RestController
@RequestMapping("test1")
public class IndexController {

    @Autowired
    IGatewayTestService gatewayTestService;

    @RequestMapping(value = "/senMsg")
    public Object senMsg(String datasourceName){

        return gatewayTestService.sendMsg();
    }
}
