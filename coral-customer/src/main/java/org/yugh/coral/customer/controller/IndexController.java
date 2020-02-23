package org.yugh.coral.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.coral.core.config.SimpleSnowFlakeGenerated;

import javax.servlet.http.HttpServletRequest;

/**
 * // 熔断测试
 *
 * @author: 余根海
 * @creation: 2019-07-01 19:03
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@RestController
@RequestMapping("cust")
//@DefaultProperties(defaultFallback = "defaultFallback")
public class IndexController {

    private final SimpleSnowFlakeGenerated simpleSnowFlakeGenerated;
    private IndexController(SimpleSnowFlakeGenerated simpleSnowFlakeGenerated){
        this.simpleSnowFlakeGenerated = simpleSnowFlakeGenerated;
    }

    @GetMapping(value = "getRandomId/{randomId}")
    public Object getCustomerById(@PathVariable("randomId") Long randomId, HttpServletRequest request) {


        log.info("request : {}" , request);

        log.info("=====================>  收到来自productId: {} ", randomId);

        if (randomId % 2 == 0) {
            throw new RuntimeException("测试异常");

        }

        Object random = simpleSnowFlakeGenerated.simpleSnowFlakeGenerated();
        return random;
    }


    private String defaultFallback() {

        return "来自customer的提示： 太拥挤了， 请稍后再试。 ";
    }



}
