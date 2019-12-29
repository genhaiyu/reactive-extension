package org.yugh.coral.boot.servlet.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yugenhai
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(attributes.getRequest(), () -> "PreFeignInterceptor RequestContextHolder attributes.getRequest() '" + attributes.getRequest() + "' is null");
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");
        // FIXME
        requestTemplate.header(token);
    }
}
