package org.yugh.common.auth.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.yugh.common.auth.Config;
import org.yugh.common.model.User;

import java.util.Map;

/**
 * @author yugenhai
 * 鉴权相关
 */
@Slf4j
public class SsoProviderImpl extends AbstractSsoProvider {
    public SsoProviderImpl(Config config) {
        super(config);
    }

    @Override
    public User login(String userName, String password, Map<String, Object> contentMap) throws Exception {
        return null;
    }

    @Override
    public User getUserByToken(String token) throws Exception {
        return null;
    }

    @Override
    public User getUserByToken(ServerHttpRequest request) throws Exception {
        return null;
    }

    @Override
    public boolean logout(String token) throws Exception {
        return false;
    }

    @Override
    public boolean logout(ServerHttpRequest request, ServerHttpResponse response) throws Exception {
        return false;
    }
    /** */

}
