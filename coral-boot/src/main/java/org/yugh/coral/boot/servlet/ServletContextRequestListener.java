package org.yugh.coral.boot.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.coral.core.common.constant.StringPool;
import org.yugh.coral.core.utils.SnowFlakeGenerateUtils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yugenhai
 */
@Slf4j
@Order(Integer.MIN_VALUE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletContextRequestListener implements ServletRequestListener {

    private static final String REQUEST_ATTRIBUTES_ATTRIBUTE =
            ServletContextRequestListener.class.getName() + ".REQUEST_ATTRIBUTES";

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        if (!(servletRequestEvent.getServletRequest() instanceof HttpServletRequest)) {
            throw new IllegalArgumentException(
                    "Request is not an HttpServletRequest: " + servletRequestEvent.getServletRequest());
        }
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        Thread thread = Thread.currentThread();
        String msgId = String.valueOf(SnowFlakeGenerateUtils.snowFlakeGenerate());
        thread.setName(msgId);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        request.setAttribute(StringPool.MSG_ID, msgId);
        request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
        log.debug("\n Start msgId:{}  ServletContextRequestListener", msgId);
        LocaleContextHolder.setLocale(request.getLocale());
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        ServletRequestAttributes attributes = null;
        Object reqAttr = servletRequestEvent.getServletRequest().getAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE);
        if (reqAttr instanceof ServletRequestAttributes) {
            attributes = (ServletRequestAttributes) reqAttr;
        }
        RequestAttributes threadAttributes = RequestContextHolder.getRequestAttributes();
        if (threadAttributes != null) {
            log.info("ServletContextRequestListener Listener's Has another to thread");
            LocaleContextHolder.resetLocaleContext();
            RequestContextHolder.resetRequestAttributes();
            if (attributes == null && threadAttributes instanceof ServletRequestAttributes) {
                attributes = (ServletRequestAttributes) threadAttributes;
            }
        }
        if (attributes != null) {
            attributes.requestCompleted();
        }
    }
}
