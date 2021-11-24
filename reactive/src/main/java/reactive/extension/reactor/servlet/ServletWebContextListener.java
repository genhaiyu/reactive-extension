package reactive.extension.reactor.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactive.extension.basement.config.DispatcherRequestCustomizer;
import reactive.extension.basement.request.RequestHeaderProvider;
import reactive.extension.basement.request.RequestMessageDefinition;
import reactive.extension.reactor.reactive.ReactiveWebContextFilter;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Reactive, {@link ReactiveWebContextFilter}.
 *
 * @author Genhai Yu
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletWebContextListener implements ServletRequestListener, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(ServletWebContextListener.class);
    private int order = Ordered.HIGHEST_PRECEDENCE + 10;

    private final DispatcherRequestCustomizer<RequestMessageDefinition.ProduceValues> dispatcherRequestCustomizer;
    private final RequestMessageDefinition.ProduceValues produceValues = new RequestMessageDefinition.ProduceValues();

    public ServletWebContextListener(DispatcherRequestCustomizer<RequestMessageDefinition.ProduceValues> dispatcherRequestCustomizer) {
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
            ServletWebContextListener.class.getName() + ".REQUEST_ATTRIBUTES";

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
