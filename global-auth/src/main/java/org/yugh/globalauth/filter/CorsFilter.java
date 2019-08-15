package org.yugh.globalauth.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yugh.globalauth.common.constants.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CORS Gateway Disable
 * <p>
 * Used this MVC {@link Filter}
 *
 * @author yugenhai
 */
@Order(3)
@Component
public class CorsFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (StringUtils.isEmpty(request.getHeader(HttpHeaders.ORIGIN))) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, Constant.XX_ALLOW_ALL);
        } else {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
        }
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, Constant.XX_ALLOW_ALL);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, Constant.XX_ALLOW_HEADERS_NEW);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, String.valueOf(Constant.COOKIE_TIME_OUT));
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
