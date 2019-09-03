package org.yugh.auth.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yugenhai
 */
@UtilityClass
public class WebUtils {


    /**
     * 根据request和sessionKey获取session
     *
     * @param request
     * @param sessionKey
     * @return sessionValue
     */
    public static Object getSession(HttpServletRequest request, String sessionKey) {
        return request.getAttribute(sessionKey);
    }

    /**
     * 保存Session值
     *
     * @param request
     * @param sessionKey
     * @param sessionValue
     */
    public static void setSession(HttpServletRequest request, String sessionKey, Object sessionValue) {
        request.setAttribute(sessionKey, sessionValue);
    }


    /**
     * 根据session名称删除session值.
     *
     * @param request
     * @param sessionKey
     */
    public static void removeSession(HttpServletRequest request, String sessionKey) {
        request.removeAttribute(sessionKey);
    }

    /**
     * 添加Cookie值
     *
     * @param response
     * @param name     cookie的名称
     * @param value    cookie的值
     * @param maxAge   cookie存放的时间
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name.trim(), value.trim());
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        //禁用JS获取COOKIE
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

}
