package org.yugh.product.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.yugh.globalauth.aspect.GlobalFeignInterceptor;
import org.yugh.product.fallback.IndexFallbackFactory;

/**
 * //
 *
 * @author: 余根海
 * @creation: 2019-07-01 19:10
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@FeignClient(name = "customer", fallbackFactory = IndexFallbackFactory.class, configuration = GlobalFeignInterceptor.class)
public interface IndexService {


    /**
     * 调用消费者
     * @param randomId
     * @return
     */
    @GetMapping("cust/getRandomId/{randomId}")
    Object getRandomId(@PathVariable("randomId") Long randomId);

}
