package org.yugh.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yugh.auth.feign.DefaultFallbackFactory;
import org.yugh.product.service.IndexService;

/**
 * //超时熔断
 *
 * @author: 余根海
 * @creation: 2019-07-01 19:14
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@Component("indexTimeOutFallbackImpl")
public class IndexTimeOutFallbackImpl extends DefaultFallbackFactory<IndexService> {

    /**@Override public Object getRandomId(Long randomId) {
    log.info("========> 超时熔断:{}", randomId);
    return "超时熔断";
    }**/
}
