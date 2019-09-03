package org.yugh.product.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yugh.auth.annotation.PreAuth;
import org.yugh.auth.util.ResultJson;
import org.yugh.product.service.IGatewayTestService;

/**
 * @author yugenhai
 */
@PreAuth
@RestController
@RequestMapping("test1")
public class LubanController {

    @Autowired
    IGatewayTestService gatewayTestService;
    @Autowired
    DataService dataService;

    @HystrixCommand
    @GetMapping(value = "/sendMsg")
    public Object sendMsg1(@RequestParam(value = "id", defaultValue = "1") Integer id,
                           @RequestParam(value = "name", defaultValue = "2") String name,
                           @RequestParam(value = "createdAtStart", defaultValue = "1") String createdAtStart,
                           @RequestParam(value = "createdAtEnd", defaultValue = "2") String createdAtEnd,
                           @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                           @RequestParam(value = "perPage", defaultValue = "10") Integer perPage) {

        return gatewayTestService.sendMsg1(id, name, createdAtStart, createdAtEnd, pageIndex, perPage);
    }

    @PostMapping(value = "/preview", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultJson preview(DataSetDTO dataSetDTO) {

        ResultJson object = dataService.preview(dataSetDTO);

        System.out.println(object);
        return object;
    }


}
