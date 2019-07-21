package org.yugh.common.auth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.yugh.common.common.constants.Constants;
import org.yugh.common.model.User;

/**
 * 鉴权相关
 *
 * @author yugenhai
 */
@Slf4j
@Component
public class SessionManager {

    @Autowired
    private SsoFinder ssoFinder;
    @Autowired
    private CookieUtils cookieUtils;


    /**
     * 获取登录的TOKEN
     *
     * @param request
     * @return
     * @author yugenhai
     */
    public boolean isLogined(ServerHttpRequest request) {
        return true;
    }


    /**
     * 注销token 以及本身的cookie
     *
     * @param request
     * @param response
     */
    public void logout(ServerHttpRequest request, ServerHttpResponse response) {
    }


    /**
     * 根据cookie获取用户名（邮箱前缀唯一）
     *
     * @param request
     * @return
     */
    public String getUserName(ServerHttpRequest request) throws Exception {
        return null;
    }


    public User getUser(ServerHttpRequest request) throws Exception {
        return null;
    }


    /**
     * 取到token
     *
     * @param request
     * @return
     * @author yugenhai
     */
    private String findByToken(ServerHttpRequest request) {
        String token = cookieUtils.getCookieByName(request, Constants.TOKEN);
        return token;
    }


}
