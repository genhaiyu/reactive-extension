/*
 * Copyright (c) 2019-2029, yugenhai108@gmail.com.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yugh.coral.boot.servlet;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.coral.core.common.constant.LogMessageInfo;
import org.yugh.coral.core.config.SimpleSnowFlakeGenerated;

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

    private final SimpleSnowFlakeGenerated simpleSnowFlakeGenerated;

    private ServletContextRequestListener(SimpleSnowFlakeGenerated simpleSnowFlakeGenerated) {
        this.simpleSnowFlakeGenerated = simpleSnowFlakeGenerated;
    }

    private static final String REQUEST_ATTRIBUTES_ATTRIBUTE =
            ServletContextRequestListener.class.getName() + ".REQUEST_ATTRIBUTES";

    /**
     * StringJoiner reactorId = new StringJoiner(StringPool.EMPTY);
     * reactorId.add(StringPool.MDC_HEADER);
     * reactorId.add(msgId);
     *
     * @param servletRequestEvent
     */
    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        if (!(servletRequestEvent.getServletRequest() instanceof HttpServletRequest)) {
            throw new IllegalArgumentException(
                    "Request is not an HttpServletRequest: " + servletRequestEvent.getServletRequest());
        }
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        /* Thread thread = Thread.currentThread(); */
        String msgId = String.valueOf(simpleSnowFlakeGenerated.simpleSnowFlakeGenerated());
        /* thread.setName(msgId); */
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        request.setAttribute(LogMessageInfo.CONTEXT_MAP, msgId);
        request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
        MDC.put(LogMessageInfo.REQUEST_ID_KEY, msgId);
        log.info("Servlet Request Start : {}", msgId);
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
            MDC.clear();
            attributes.requestCompleted();
        }
    }
}
