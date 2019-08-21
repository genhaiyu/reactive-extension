package org.yugh.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yugh.auth.common.constants.Constant;
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
 * @author yugenhai
 */
@Slf4j
@Component
public class AuthService {

    @Autowired
    private AuthCookieUtils authCookieUtils;
    private HttpClientHelper httpClientHelper;
    @Autowired
    private SSOConfig properties;

    public AuthService() {
        this.httpClientHelper = new HttpClientHelper(10000, 1000 * 60);
    }


    /**
     * web -> webflux
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String token;
        if ((token = request.getHeader(Constant.DATAWORKS_GATEWAY_HEADERS)) != null) {
            return token;
        }
        //cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies).filter(cookie -> Constant.SESSION_xx_TOKEN.equals(cookie.getName())).map(Cookie::getValue).findFirst().orElse(null);
        }
        return null;
    }


    /**
     * 网关登录用户
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
     * 网关获取用户
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
     * 网关获取token
     *
     * @param request
     * @return
     */
    public String getUserTokenByGateway(ServerHttpRequest request) {
        return authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_xx_TOKEN);
    }


    /**
     * 网关下线用户
     *
     * @param request
     * @param response
     */
    public void logoutByGateway(ServerHttpRequest request, ServerHttpResponse response) {
        String token = authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_xx_TOKEN);
        authCookieUtils.removeCookieByReactive(request, response, null, Constant.SESSION_xx_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return;
        } else {
            try {
                this.logoutByToken(token);
            } catch (Exception e) {
                log.error("注销失败 : {}", e);
                throw new RuntimeException("注销失败 : {}" + e.getMessage());
            }
        }

    }


    /**
     * 移除所有的token
     *
     * @param request
     * @param response
     */
    public void removeCookieByAspect(HttpServletRequest request, HttpServletResponse response) {
        authCookieUtils.removeCookie(request, response, "/", Constant.SESSION_xx_TOKEN);
    }


    /**
     * 拦截器获取用户
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
     * 判断是否存在登录token
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
     * 根据token调用SSO获取用户信息
     *
     * @param token
     * @return
     */
    public User getUserByToken(String token) {
        Map<String, Object> paramsMap = new HashMap(16);
        paramsMap.put("token", token);
        //FIXME 动态选择
        SignUtils.addSignatureWithoutEnter(paramsMap, properties.getSsoTestAppKey(), properties.getSsoTestAppSecret());
        String resp = httpClientHelper.doPost(properties.getSsoTestIdentity(), new HashMap(16), paramsMap);
        return parseAsUser(resp);
    }


    /**
     * HTTP5.0之前的下线
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public boolean logoutByRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie cookie = authCookieUtils.getCookieByName(request, Constant.SESSION_xx_TOKEN);
        String token = cookie != null ? cookie.getValue() : null;
        if (StringUtils.isEmpty(token)) {
            return true;
        } else {
            Map paramsMap = new HashMap<String, Object>();
            paramsMap.put("token", token);
            //FIXME 动态选择
            SignUtils.addSignatureWithoutEnter(paramsMap, properties.getSsoTestAppKey(), properties.getSsoTestAppSecret());
            String resp = httpClientHelper.doPost(properties.getSsoTestLogout(), new HashMap(16), paramsMap);
            if (StringUtils.isEmpty(resp)) {
                throw new Exception("单点登录下线失败");
            }
            Map respMap = JsonUtils.jsonToObject(resp, Map.class);
            Object status = respMap.get("status");
            if (status == null || (!status.toString().equalsIgnoreCase("true"))) {
                Object msg = respMap.get("msg");
                log.error(msg == null ? "单点登录下线失败" : (String) msg);
                return false;
            }
        }
        authCookieUtils.removeCookie(request, response, null, Constant.SESSION_xx_TOKEN);
        return true;
    }


    /**
     * 根据token调用下线
     *
     * @param token
     * @return
     * @throws Exception
     */
    public boolean logoutByToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            return true;
        } else {
            Map paramsMap = new HashMap<String, Object>();
            paramsMap.put("token", token);
            //FIXME 动态选择
            SignUtils.addSignatureWithoutEnter(paramsMap, properties.getSsoTestAppKey(), properties.getSsoTestAppSecret());
            String resp = httpClientHelper.doPost(properties.getSsoTestLogout(), new HashMap(16), paramsMap);
            if (resp == null) {
                throw new Exception("单点登录下线失败");
            }
            Map respMap = JsonUtils.jsonToObject(resp, Map.class);
            Object status = respMap.get("status");
            if (status == null || (!status.toString().equalsIgnoreCase("true"))) {
                Object msg = respMap.get("msg");
                log.error(msg == null ? "单点登录下线失败" : (String) msg);
                return false;
            }
        }
        return true;
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
        if (status == null || ("false").equalsIgnoreCase(status.toString())) {
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
        String isEhr = (String) userInfo.get("is_ehr");
        String personType = (String) userInfo.get("person_type");
        Boolean isSameDevice = (Boolean) userInfo.get("is_same_device");
        User user = User.builder().build();
        user.setNo(userId);
        user.setUserName(userNameEn);
        user.setAliasName(userNameCn);
        user.setEmail(email);
        return user;
    }
}
