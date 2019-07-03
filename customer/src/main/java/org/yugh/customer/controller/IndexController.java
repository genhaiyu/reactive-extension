package org.yugh.customer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.customer.utils.SnowFlakeUtil;

/**
 * //
 *
 * @author: 余根海
 * @creation: 2019-07-01 19:03
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@RestController
@Api(description = "消费控制器")
@RequestMapping("cust")
@DefaultProperties(defaultFallback = "defaultFallback")
public class IndexController {


    @GetMapping(value = "getRandomId/{randomId}")
    public Object getCustomerById(@PathVariable("randomId") Long randomId) {


        log.info("=====================>  收到来自productId: {} ", randomId);

        if (randomId % 2 == 0) {
            throw new RuntimeException("测试异常");

        }

        Object random = SnowFlakeUtil.nextWaterFlow();
        return random;
    }


    private String defaultFallback() {

        return "来自customer的提示： 太拥挤了， 请稍后再试。 ";
    }

}
