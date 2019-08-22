package org.yugh.product.fallback;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yugh.product.service.IndexService;

import javax.annotation.Resource;

/**
 * //断路器工厂定制实现
 *
 * @author: 余根海
 * @creation: 2019-06-24 20:23
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
//@Component
public class IndexFallbackFactory implements FallbackFactory<IndexService> {

    private final static String TIME_OUT = "Read time out";

    @Resource(name = "indexTimeOutFallbackImpl")
    private IndexService indexTimeOutFallbackService;
    @Resource(name = "indexErrorFallbackImpl")
    private IndexService indexFallbackService;


    @Override
    public IndexService create(Throwable throwable) {
        log.error("Feign getCustomerById 进入熔断工厂错误 = {}", throwable.getMessage());
        if(null != throwable.getMessage() && throwable.getMessage().contains(TIME_OUT)){
            return indexTimeOutFallbackService;
        }else {
            return indexFallbackService;
        }
    }
}
