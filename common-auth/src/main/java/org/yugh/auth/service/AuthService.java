package org.yugh.auth.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.yugh.auth.common.constants.Constant;
import org.yugh.auth.config.AuthConfig;
import org.yugh.auth.pojo.dto.UserDTO;
import org.yugh.auth.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private JwtHelper jwtHelper;


    /**
     * doSomeThing
     *
     * @param paramsMap
     * @param appKey
     * @param secret
     */
    private void doSomeThing(Map<String, Object> paramsMap, String appKey, String secret) {
        log.info("doSomeThing for map-> appKey-> secret");
    }

    /**
     * doSomeThing
     *
     * @param identity
     * @param map
     * @param paramsMap
     * @return
     */
    private String doSomeThing(String identity, HashMap map, Map<String, Object> paramsMap) {
        return "do something!";
    }


    /**
     * token 转 对象
     *
     * @param request
     * @return
     */
    public UserDTO parseUserToJwt(HttpServletRequest request) {
        String token = this.getTokenByHeader(request);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            Claims claims = jwtHelper.getAllClaimsFromToken(token);
            Map<String, Object> userMap = (Map<String, Object>) claims.get(StringPool.DATAWORKS_USER_INFO);
            UserDTO userDTO = (UserDTO) BeanMapUtils.map2Object(userMap, UserDTO.class);
            return userDTO;
        } catch (Exception e) {
            log.error("parseUserToJwt Exception :{}", e.getMessage());
        }
        return null;
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        return jwtHelper.validateToken(token);
    }


    /**
     * Get Jwt
     *
     * @param request
     * @return
     */
    public String getTokenByHeader(HttpServletRequest request) {
        String userToken;
        if (!StringUtils.isEmpty(userToken = request.getHeader(StringPool.DATAWORKS_TOKEN))) {
            return userToken;
        }
        if (!StringUtils.isEmpty((userToken = (String) request.getAttribute(StringPool.DATAWORKS_TOKEN)))) {
            return userToken;
        }
        return null;
    }



    /**
     * Get token By Gateway
     *
     * @param request
     * @return
     */
    @Deprecated
    public String getToken(HttpServletRequest request) {
        String token;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies).filter(cookie -> Constant.SESSION_TOKEN.equals(cookie.getName())).map(Cookie::getValue).findFirst().orElse(null);
        }
        if (!StringUtils.isEmpty(token = (String) request.getAttribute(Constant.SESSION_TOKEN))) {
            return token;
        }
        if (!StringUtils.isEmpty((token = request.getParameter(Constant.SESSION_TOKEN)))) {
            return token;
        }
        if (!StringUtils.isEmpty(request.getHeader(Constant.DATAWORKS_GATEWAY_HEADERS)) &&
                !StringUtils.isEmpty(token = request.getHeader(Constant.SESSION_TOKEN))) {
            return token;
        }
        if (!StringUtils.isEmpty((token = request.getHeader(Constant.SESSION_TOKEN)))) {
            WebUtils.setSession(request, StringPool.FEIGN, token);
            return token;
        }
        return null;
    }


    /**
     * Gateway Login
     *
     * @param request
     * @return
     */
    public boolean isLoginByReactive(ServerHttpRequest request) {
        String token = authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            UserDTO userDTO = this.getUserByToken(token);
            if (StringUtils.isEmpty(userDTO)) {
                return false;
            }
        } catch (Exception e) {
            log.error("getUserByToken Failed !!!", e);
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
    public UserDTO getUserByReactive(ServerHttpRequest request) {
        String token = authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            UserDTO userDTO = this.getUserByToken(token);
            if (StringUtils.isEmpty(userDTO)) {
                return null;
            }
            return userDTO;
        } catch (Exception e) {
            log.error("getUserByToken Failed !!!", e);
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
        String token = authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return;
        } else {
            try {
                this.logoutByToken(token);
                //this.removeCookieByGateway(response);
            } catch (Exception e) {
                log.error("logoutByToken Failed !!!", e);
                throw new RuntimeException("logoutByToken Failed !!!" + e.getMessage());
            }
        }
    }


    /*******************************************************private*************************************************/

    /**
     * Logout By token
     *
     * @param token
     * @return
     * @throws Exception
     * @author yugenhai
     */
    private boolean logoutByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return true;
        } else {
            Map<String, Object> paramsMap = new HashMap(16);
            paramsMap.put("token", token);
            String env = authConfig.getEnvSwitch();
            Assert.hasText(env, "envSwitch is Empty");
            switch (env) {
                case StringPool
                        .TEST:
                    return this.logoutByTestOrProd(paramsMap, authConfig.getSsoTestAppKey(), authConfig.getSsoTestAppSecret(), authConfig.getSsoTestIdentity());
                case StringPool
                        .PROD:
                    return this.logoutByTestOrProd(paramsMap, authConfig.getSsoProdAppKey(), authConfig.getSsoProdAppSecret(), authConfig.getSsoProdIdentity());
                default:
                    return false;
            }
        }
    }


    /**
     * GetUserByToken
     *
     * @param token
     * @return
     */
    private UserDTO getUserByToken(String token) {
        Map<String, Object> paramsMap = new HashMap(16);
        paramsMap.put("token", token);
        String env = authConfig.getEnvSwitch();
        Assert.hasText(env, "envSwitch is Empty");
        String resp;
        switch (env) {
            case StringPool
                    .TEST:
                this.doSomeThing(paramsMap, authConfig.getSsoTestAppKey(), authConfig.getSsoTestAppSecret());
                resp = this.doSomeThing(authConfig.getSsoTestIdentity(), new HashMap(16), paramsMap);
                return parseAsUser(resp);
            case StringPool
                    .PROD:
                this.doSomeThing(paramsMap, authConfig.getSsoProdAppKey(), authConfig.getSsoProdAppSecret());
                resp = this.doSomeThing(authConfig.getSsoProdIdentity(), new HashMap(16), paramsMap);
                return parseAsUser(resp);
            default:
                return null;
        }
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
        this.doSomeThing(paramsMap, appKey, secret);
        String resp = this.doSomeThing(identity, new HashMap(16), paramsMap);
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
     * 解析SSO返回的用户信息
     *
     * @param userJson
     * @return
     */
    private UserDTO parseAsUser(String userJson) {
        return new UserDTO();
    }


    /**
     * HttpServletResponse Remove Cookies
     *
     * @param response
     */
    @Deprecated
    private void removeCookieByAspect(HttpServletResponse response) {
        this.authCookieUtils.removeCookie(response, authConfig.getGatewayCloud(), Constant.SESSION_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getGatewayApps(), Constant.SESSION_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getXxCorp(), Constant.SESSION_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getXxCloud(), Constant.SESSION_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getXxApps(), Constant.SESSION_TOKEN);
        this.authCookieUtils.removeCookie(response, authConfig.getXxCom(), Constant.SESSION_TOKEN);
    }

    /**
     * Reactive Remove Cookies
     *
     * @param response
     */
    @Deprecated
    private void removeCookieByGateway(ServerHttpResponse response) {
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_TOKEN, null, authConfig.getGatewayCloud());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_TOKEN, null, authConfig.getGatewayApps());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_TOKEN, null, authConfig.getXxApps());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_TOKEN, null, authConfig.getXxCloud());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_TOKEN, null, authConfig.getXxCorp());
        this.authCookieUtils.removeCookieByReactive(response, Constant.SESSION_TOKEN, null, authConfig.getXxCom());
    }


    /**
     * Gateway Get token
     *
     * @param request
     * @return
     */
    @Deprecated
    private String getUserTokenByGateway(ServerHttpRequest request) {
        return this.authCookieUtils.getCookieByNameByReactive(request, Constant.SESSION_TOKEN);
    }
}
