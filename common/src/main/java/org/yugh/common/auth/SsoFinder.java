package org.yugh.common.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yugh.common.auth.provider.AbstractSsoProvider;

/**
 * 鉴权相关
 *
 * @author yugenhai
 */
@Slf4j
@Component
public class SsoFinder {

    @Autowired
    private Config config;

    public AbstractSsoProvider getSSOProvider() {
        return null;
    }

}
