/*
 * public boolean logoutByRequest(HttpServletRequest request, HttpServletResponse response) {
 * Cookie cookie = authCookieUtils.getCookieByName(request, Constant.SESSION_xx_TOKEN);
 * String token = cookie != null ? cookie.getValue() : null;
 * if (StringUtils.isEmpty(token)) {
 * return true;
 * } else {
 * Map paramsMap = new HashMap<String, Object>(16);
 * paramsMap.put("token", token);
 * String env = authConfig.getEnvSwitch();
 * switch (env) {
 * case "test":
 * boolean test = this.logoutByTestOrProdAndRequest(paramsMap, authConfig.getSsoTestAppKey(), authConfig.getSsoTestAppSecret(), authConfig.getSsoTestIdentity());
 * if (test) {
 * this.removeCookieByAspect(response);
 * return true;
 * }
 * return false;
 * case "prod":
 * boolean prod = this.logoutByTestOrProdAndRequest(paramsMap, authConfig.getSsoProdAppKey(), authConfig.getSsoProdAppSecret(), authConfig.getSsoProdIdentity());
 * if (prod) {
 * this.removeCookieByAspect(response);
 * return true;
 * }
 * default:
 * return false;
 * }
 * }
 * }
 */
package org.yugh.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yugh.auth.common.constants.Constant;
import org.yugh.auth.config.AuthConfig;
import org.yugh.auth.pojo.dto.User;
import org.yugh.auth.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Main auth service
 * <p>
 * <p>
 * Servlet  --- > Reactive
 *
 * @author yugenhai
 */
@Slf4j
@Component
public class AuthService {

    @Autowired
    private AuthCookieUtils authCookieUtils;
    private HttpClientHelper httpClientHelper;
    @Autowired
    private AuthConfig authConfig;

    public AuthService() {
        this.httpClientHelper = new HttpClientHelper(10000, 1000 * 60);
    }


    /**
     * Get token By Gateway
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String token;
        if ((token = request.getHeader(Constant.DATAWORKS_GATEWAY_HEADERS)) != null) {
            return token;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies).filter(cookie -> Constant.SESSION_xx_TOKEN.equals(cookie.getName())).map(Cookie::getValue).findFirst().orElse(null);
        }
        return null;
    }


    /**
     * HttpServletResponse Remove Cookies
     *
     * @param response
     */
    public void removeCookieByAspect(HttpServletResponse response) {
        this.authCookieUtils.removeCookie(response, authConfig.getGatewayCloud(), Constant.SESSION_xx_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getGatewayApps(), Constant.SESSION_xx_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getXxCorp(), Constant.SESSION_xx_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getXxCloud(), Constant.SESSION_xx_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getXxApps(), Constant.SESSION_xx_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getXxCom(), Constant.SESSION_xx_TOKEN);
    }

    /**
     * Reactive Remove Cookies
     *
     * @param response
     */
    private void removeCookieByGateway(ServerHttpResponse response) {
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_xx_TOKEN, null, authConfig.getGatewayCloud());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_xx_TOKEN, null, authConfig.getGatewayApps());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_xx_TOKEN, null, authConfig.getXxApps());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_xx_TOKEN, null, authConfig.getXxCloud());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_xx_TOKEN, null, authConfig.getXxCorp());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_xx_TOKEN, null, authConfig.getXxCom());
    }


    /**
     * Check Env
     *
     * @param paramsMap
     * @param appKey
     * @param secret
     * @param identity
     * @return
     * @author yugenhai
     */
    private boolean logoutByTestOrProd(Map<String, Object> paramsMap, String appKey, String secret, String
            identity) {
        SignUtils.addSignatureWithoutEnter(paramsMap, appKey, secret);
        String resp = httpClientHelper.doPost(identity, new HashMap(16), paramsMap);
        if (StringUtils.isEmpty(resp)) {
            throw new RuntimeException("SSO Logout Failed !!!");
        }
        Map respMap = JsonUtils.jsonToObject(resp, Map.class);
        Object status = respMap.get("status");
        if (status == null || (!status.toString().equalsIgnoreCase(StringPool.TRUE))) {
            Object msg = respMap.get("msg");
            log.error(msg == null ? "SSO Logout Failed !!!" : (String) msg);
            return false;
        }
        return true;
    }


    /**
     * Gateway Get token
     *
     * @param request
     * @return
     */
    public String getUserTokenByGateway(ServerHttpRequest request) {
        return this.authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_xx_TOKEN);
    }


    /**
     * Gateway Login
     *
     * @param request
     * @return
     */
    public boolean isLoginByReactive(ServerHttpRequest request) {
        String token = authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_xx_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        User user = null;
        try {
            user = this.getUserByToken(token);
            if (StringUtils.isEmpty(user)) {
                return false;
            }
        } catch (Exception e) {
            log.error("获取SSO用户信息失败", e);
            return false;
        }
        return true;
    }


    /**
     * Gateway Get user
     *
     * @param request
     * @return
     */
    public User getUserByReactive(ServerHttpRequest request) {
        String token = authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_xx_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = null;
        try {
            user = this.getUserByToken(token);
            if (StringUtils.isEmpty(user)) {
                return null;
            }
            return user;
        } catch (Exception e) {
            log.error("获取SSO用户信息失败", e);
            return null;
        }
    }


    /**
     * Gateway logout User
     *
     * @param request
     * @param response
     */
    public void logoutByGateway(ServerHttpRequest request, ServerHttpResponse response) {
        String token = authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_xx_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return;
        } else {
            try {
                this.logoutByToken(token);
                this.removeCookieByGateway(response);
            } catch (Exception e) {
                log.error("注销失败 : {}", e);
                throw new RuntimeException("注销失败 : {}" + e.getMessage());
            }
        }
    }


    /**
     * Aspect Get token
     *
     * @param request
     * @return
     */
    public User getUserByAuthToken(HttpServletRequest request) {
        Cookie cookie = authCookieUtils.getCookieByName(request, Constant.SESSION_xx_TOKEN);
        String token = cookie != null ? cookie.getValue() : null;
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = null;
        try {
            user = this.getUserByToken(token);
            if (Objects.nonNull(user)) {
                return user;
            }
        } catch (Exception e) {
            log.error("获取SSO用户信息失败", e);
            return null;
        }
        return null;
    }


    /**
     * Check login
     *
     * @param request
     * @return
     */
    public boolean isLogin(HttpServletRequest request) {
        Cookie cookie = authCookieUtils.getCookieByName(request, Constant.SESSION_xx_TOKEN);
        String token = cookie != null ? cookie.getValue() : null;
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        User user = null;
        try {
            user = this.getUserByToken(token);
            if (Objects.isNull(user)) {
                return false;
            }
        } catch (Exception e) {
            log.error("获取SSO用户信息失败", e);
            return false;
        }
        return true;
    }


    /**
     * GetUserByToken
     *
     * @param token
     * @return
     */
    public User getUserByToken(String token) {
        Map<String, Object> paramsMap = new HashMap(16);
        paramsMap.put("token", token);
        String env = authConfig.getEnvSwitch();
        if (StringUtils.isEmpty(env)) {
            throw new RuntimeException("!!!!!!!!!!! Not Found env !!!!!!!!!!!");
        }
        String resp;
        switch (env) {
            case StringPool
                    .TEST:
                SignUtils.addSignatureWithoutEnter(paramsMap, authConfig.getSsoTestAppKey(), authConfig.getSsoTestAppSecret());
                resp = httpClientHelper.doPost(authConfig.getSsoTestIdentity(), new HashMap(16), paramsMap);
                return parseAsUser(resp);
            case StringPool
                    .PROD:
                SignUtils.addSignatureWithoutEnter(paramsMap, authConfig.getSsoProdAppKey(), authConfig.getSsoProdAppSecret());
                resp = httpClientHelper.doPost(authConfig.getSsoProdIdentity(), new HashMap(16), paramsMap);
                return parseAsUser(resp);
            default:
                return null;
        }
    }


    /**
     * Logout By token
     *
     * @param token
     * @return
     * @throws Exception
     * @author yugenhai
     */
    public boolean logoutByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return true;
        } else {
            Map<String, Object> paramsMap = new HashMap(16);
            paramsMap.put("token", token);
            String env = authConfig.getEnvSwitch();
            switch (env) {
                case StringPool
                        .TEST:
                    boolean test = this.logoutByTestOrProd(paramsMap, authConfig.getSsoTestAppKey(), authConfig.getSsoTestAppSecret(), authConfig.getSsoTestIdentity());
                    if (test) {
                        return true;
                    }
                    return false;
                case StringPool
                        .PROD:
                    boolean prod = this.logoutByTestOrProd(paramsMap, authConfig.getSsoProdAppKey(), authConfig.getSsoProdAppSecret(), authConfig.getSsoProdIdentity());
                    if (prod) {
                        return true;
                    }
                default:
                    return false;
            }
        }
    }

    /**
     * 解析SSO返回的用户信息
     *
     * @param userJSON
     * @return
     */
    private User parseAsUser(String userJSON) {
        Map respMap = JsonUtils.jsonToObject(userJSON, Map.class);
        Object status = respMap.get("status");
        if (status == null || (StringPool.FALSE).equalsIgnoreCase(status.toString())) {
            log.error("get user info error: " + userJSON);
            Object errorMsgObject = respMap.get("msg");
            String errorMsg = "get user info failed";
            if (errorMsgObject != null) {
                errorMsg = String.valueOf(errorMsgObject);
            }
            try {
                throw new RuntimeException(errorMsg);
            } catch (Exception e) {
                log.error("get user info error: " + userJSON);
                throw new RuntimeException(errorMsg);
            }
        }
        Map userInfo = (Map) respMap.get("userInfo");
        String userId = (String) userInfo.get("user_id");
        String email = (String) userInfo.get("email");
        String userNameEn = email.replace("@xx.com", "");
        String userNameCn = (String) userInfo.get("fullname");
        User user = User.builder().build();
        user.setNo(userId);
        user.setUserName(userNameEn);
        user.setAliasName(userNameCn);
        user.setEmail(email);
        return user;
    }
}
