package org.yugh.globalauth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yugh.globalauth.common.constants.Constant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author yugenhai
 */
@Slf4j
@Component
public class AuthService {


    /**
     * web -> webflux
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String token;
        if ((token = request.getHeader(Constant.TOKEN)) != null) {
            return token;
        }
        if ((token = request.getParameter(Constant.TOKEN)) != null) {
            return token;
        }
        //cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies).filter(cookie -> Constant.TOKEN.equals(cookie.getName())).map(Cookie::getValue).findFirst().orElse(null);
        }
        return null;
    }


}
