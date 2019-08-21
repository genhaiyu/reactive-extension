package org.yugh.auth.util;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthCookieUtils {


    private boolean cookieHttpOnly;

    private boolean cookieSecure;

    static final String DEFAULT_CSRF_COOKIE_PATH = "/";


    /**
     * 将response写入cookie并设置有效期
     *
     * @param response
     * @param name
     * @param value
     * @param domain
     * @param maxAge
     */
    public void addCookie(HttpServletResponse response, String name, String value, String domain, Integer maxAge) {
        /**Cookie cookie = new Cookie(name, value);
         cookie.setPath("/");
         if (!StringUtils.isEmpty(domain)) {
         cookie.setDomain(domain);
         }
         if (maxAge != null) {
         cookie.setMaxAge(maxAge);
         }
         response.addCookie(cookie);*/
        Cookie cookie = new Cookie(name.trim(), value.trim());
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 将response里的会话置为实时失效
     * 将根下的cookie置为失效
     *
     * @param response
     * @param domain
     * @param name
     */
    public void removeCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name) {
        /**Cookie cookie = new Cookie(name, null);
         cookie.setPath("/");
         if (!StringUtils.isEmpty(domain)) {
         cookie.setDomain(domain);
         }
         cookie.setMaxAge(0);
         response.addCookie(cookie);*/
        Cookie[] cookies = request.getCookies();
        if (!StringUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    //  break;
                }
            }
        }
    }

    /**
     * 根据 XXSSO 获取cookie
     *
     * @param request
     * @param name
     * @return
     */
    public Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 读取会话里的cookie
     *
     * @param request
     * @return
     */
    public Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap(16);
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }


    /**********************reactive******************************/


    /**
     * @param request
     * @param name
     * @return
     * @author yugenhai
     */
    public String getCookieByNameByReactive(ServerHttpRequest request, String name) {
        Map<String, String> cookieMap = parseCookiesByReactive(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else {
            return null;
        }
    }


    /**
     * @param request
     * @return
     * @author yugenhai
     */
    private Map<String, String> parseCookiesByReactive(ServerHttpRequest request) {
        Map<String, String> cookieMap = new HashMap(16);
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (!CollectionUtils.isEmpty(cookies)) {
            cookies.forEach((k, v) ->
            {
                List<HttpCookie> cookieList = v.stream().collect(Collectors.toList());
                Optional<HttpCookie> cookie = cookieList.stream().parallel().findFirst();
                cookieMap.put(cookie.get().getName(), cookie.get().getValue());
            });
        }
        return cookieMap;
    }


    /**
     * HTTP 5.0
     * Logout session
     *
     * @param response
     * @param cookieValue
     * @param cookieName
     * @author yugenhai
     */
    public void removeCookieByReactive(ServerHttpRequest request, ServerHttpResponse response, String cookieValue, String cookieName) {
        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(cookieName, cookieValue)
                .path(DEFAULT_CSRF_COOKIE_PATH)
                .httpOnly(true)
                .secure(cookieSecure);
        cookieBuilder.maxAge(0);
        response.addCookie(cookieBuilder.build());
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        cookies.isEmpty();
    }

}
