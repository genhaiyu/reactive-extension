package org.yugh.gatewaynew.mapping;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yugh.gatewaynew.feign.IGatewayTestService;
import org.yugh.globalauth.annotation.PreAuth;
import org.yugh.globalauth.hystrix.AbstractHystrixController;

/**
 * Must extends AbstractHystrixController
 * <p>
 * Open Hystrix
 *
 * @author yugenhai
 */
@Deprecated
@PreAuth
@CrossOrigin
@RestController
@RequestMapping("test1")
public class IndexController extends AbstractHystrixController {

    @Autowired
    IGatewayTestService gatewayTestService;

    @HystrixCommand()
    @GetMapping(value = "/sendMsg")
    public Object sendMsg1(@RequestParam(value = "id", defaultValue = "1") Integer id,
                          @RequestParam(value = "name", defaultValue = "2") String name,
                          @RequestParam(value = "createdAtStart", defaultValue = "1") String createdAtStart,
                          @RequestParam(value = "createdAtEnd", defaultValue = "2") String createdAtEnd,
                          @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                          @RequestParam(value = "perPage", defaultValue = "10") Integer perPage) {

        return gatewayTestService.sendMsg1(id, name, createdAtStart, createdAtEnd, pageIndex, perPage);
    }
}
