package org.yugh.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.product.service.IndexService;

import javax.servlet.http.HttpServletRequest;

/**
 * //
 *
 * @author: 余根海
 * @creation: 2019-07-01 16:27
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@RestController
//@Api(description = "index控制器")
@RequestMapping("pro")
//@DefaultProperties(defaultFallback = "defaultFallback")
public class IndexController {

    private final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }


    @GetMapping("index1")
    public Object index1(HttpServletRequest request) {
        log.info("========> url:{}", request.getServletPath());
        return request.getServletPath();
    }


    /**
     * HystrixCommand  会有依赖隔离， 隔离到一个线程以内， 不影响其他函数调用。
     *
     * @param randomId
     * @return
     */
    /*@HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //开启熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"), //最近10次服务
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "20"),//最近10次服务，请求的成功率， 达到就熔断
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "2000"),// 休眠10秒后， 重新探测是否需要熔断。

    })*/
    // @ApiOperation(value = "拿到一个随机数", notes = "测试")
    @GetMapping("/index2/{randomId}")
    public String info(@PathVariable("randomId") Long randomId, HttpServletRequest request) {
        log.info("request : {}", request);

        Object id = indexService.getRandomId(randomId);
        log.info("========> 拿到来自customer的一个随机数:{}", id);
        return "拿到来自customer的randomId:" + id;

    }


    private String fallback(Long productId) {

        return "太拥挤了， 请稍后再试。";
    }

    private String defaultFallback() {
        log.info("来自product的提示： 太拥挤了， 请稍后再试。");

        return "来自product的提示： 太拥挤了， 请稍后再试。 ";
    }

}
