package org.yugh.product.service.impl;

import org.springframework.stereotype.Component;
import org.yugh.auth.factory.DefaultFallbackFactory;
import org.yugh.product.service.SqlExecute;

/**
 * @author yugenhai
 */
@Component
public class SqlServiceImpl extends DefaultFallbackFactory<SqlExecute> {
}
