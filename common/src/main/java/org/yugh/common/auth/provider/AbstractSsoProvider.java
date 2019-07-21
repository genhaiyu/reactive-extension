package org.yugh.common.auth.provider;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.yugh.common.auth.Config;
import org.yugh.common.model.User;

import java.util.Map;

public abstract class AbstractSsoProvider {

    public final static int ERROR_CODE_LOGIN_INVALID = 401;
    public final static int ERROR_CODE_REQUEST_USER_INVALID = 801;

    protected Config config;

    public AbstractSsoProvider(Config config) {
        this.config = config;
    }

    public abstract User login(String userName, String password, Map<String, Object> contentMap) throws Exception;

    public abstract User getUserByToken(String token) throws Exception;

    public abstract User getUserByToken(ServerHttpRequest request) throws Exception;

    public abstract boolean logout(String token) throws Exception;

    public abstract boolean logout(ServerHttpRequest request, ServerHttpResponse response) throws Exception;

}
