package reactive.extension.reactor.config;

import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;

/**
 * Exclusive the Jetty server.
 *
 * @author Genhai Yu
 */
public class EmbeddedContainerInitiation implements Ordered {

    private static final String STARTUP_TOMCAT = "org.apache.catalina.startup.Tomcat";
    private int order = Ordered.HIGHEST_PRECEDENCE + 1;

    public static void exclusiveJetty() {
        if (ClassUtils.isPresent(STARTUP_TOMCAT, null)) {
            throw new UnsupportedOperationException("Container unSupport the Tomcat.");
        }
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
