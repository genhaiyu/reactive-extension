package org.yugh.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yugh.auth.feign.DefaultFallbackFactory;
import org.yugh.product.service.IndexService;

/**
 * //错误熔断
 *
 * @author: 余根海
 * @creation: 2019-07-01 19:17
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@Component("indexErrorFallbackImpl")
public class IndexErrorFallbackImpl extends DefaultFallbackFactory<IndexService> {


   /** @Override
    public Object getRandomId(Long randomId) {
        log.info("========> 业务熔断:{}", randomId);
        return "业务熔断";
    }**/
}
