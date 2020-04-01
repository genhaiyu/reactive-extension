/*
 *
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 */


package org.yugh.coral.boot.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.coral.boot.reactive.ReactiveRequestContextFilter;
import org.yugh.coral.core.config.DispatcherRequestCustomizer;
import org.yugh.coral.core.config.RequestAdapterProvider;
import org.yugh.coral.core.config.RequestHeaderProvider;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Update not support Servlet
 *
 * Reactive, {@link ReactiveRequestContextFilter}
 *
 * @author yugenhai
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletRequestContextListener implements ServletRequestListener, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(ServletRequestContextListener.class);
    // filter should be set
    private int order = Ordered.HIGHEST_PRECEDENCE + 10;

    private final DispatcherRequestCustomizer<RequestAdapterProvider.ProduceValues> dispatcherRequestCustomizer;
    private final RequestAdapterProvider.ProduceValues produceValues = new RequestAdapterProvider.ProduceValues();
    public ServletRequestContextListener(DispatcherRequestCustomizer<RequestAdapterProvider.ProduceValues> dispatcherRequestCustomizer) {
        this.dispatcherRequestCustomizer = dispatcherRequestCustomizer;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    private static final String REQUEST_ATTRIBUTES_ATTRIBUTE =
            ServletRequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES";

    /**
     * @param servletRequestEvent
     */
    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        if (!(servletRequestEvent.getServletRequest() instanceof HttpServletRequest)) {
            throw new IllegalArgumentException(
                    "Request is not an HttpServletRequest: " + servletRequestEvent.getServletRequest());
        }

        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        dispatcherRequestCustomizer.customize(produceValues);
        String idProvider = produceValues.getMessageId();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        request.setAttribute(RequestHeaderProvider.CONTEXT_MAP, idProvider);
        request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
        MDC.put(RequestHeaderProvider.REQUEST_ID_KEY, idProvider);
        LOG.info("Servlet Request Started : {}", idProvider);
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
            LOG.info("ServletContextRequestListener Listener's Has another to thread");
            LocaleContextHolder.resetLocaleContext();
            RequestContextHolder.resetRequestAttributes();
            if (attributes == null && threadAttributes instanceof ServletRequestAttributes) {
                attributes = (ServletRequestAttributes) threadAttributes;
            }
        }
        if (attributes != null) {
            MDC.clear();
            attributes.requestCompleted();
        }
    }
}
