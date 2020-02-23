package org.yugh.coral.product.service.impl;

import org.springframework.stereotype.Component;
import org.yugh.coral.core.factory.feign.DefaultFallbackFactory;
import org.yugh.coral.product.service.IndexService;

/**
 * @author yugenhai
 */
@Component
public class IndexServiceImpl extends DefaultFallbackFactory<IndexService> {
}
