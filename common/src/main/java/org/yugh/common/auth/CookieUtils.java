package org.yugh.common.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * //会话相关
 *
 * @author yugenhai
 */
@Slf4j
@Component
public class CookieUtils {


    /**
     * @param request
     * @param name
     * @return
     * @author yugenhai
     */
    public String getCookieByName(ServerHttpRequest request, String name) {
        Map<String, String> cookieMap = parseCookies(request);
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
    private static Map<String, String> parseCookies(ServerHttpRequest request) {
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


    public static void removeCookie(ServerHttpResponse response, String domain, String name) {
        ResponseCookie cookie = ResponseCookie.from(name, domain).build();
        cookie.getPath().isEmpty();
        cookie.getMaxAge().isZero();
        if (!Objects.isNull(domain)) {
            cookie.getDomain().isEmpty();
        }
        response.addCookie(cookie);
    }

}
