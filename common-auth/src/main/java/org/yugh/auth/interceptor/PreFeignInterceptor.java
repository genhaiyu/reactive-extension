package org.yugh.auth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.auth.common.constants.Constant;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * dataWorks feign aspect
 *
 * @author yugenhai
 */
@Slf4j
@Component
public class PreFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (null != headerNames) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }
            Enumeration<String> attributeNames = request.getAttributeNames();
            if (null != attributeNames) {
                while (attributeNames.hasMoreElements()) {
                    String attrName = attributeNames.nextElement();
                    if (Constant.USER_INFO.equals(attrName)) {
                        requestTemplate.header(attrName, request.getAttribute(Constant.USER_INFO).toString());
                        break;
                    }
                }
            }
        }
    }
}
