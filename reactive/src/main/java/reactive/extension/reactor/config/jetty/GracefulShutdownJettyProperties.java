package reactive.extension.reactor.config.jetty;

import reactive.extension.reactor.config.EmbeddedContainerInitiation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.StringJoiner;

/**
 * Server of the Jetty properties and configuration.
 *
 * @author Genhai Yu
 */
@ConfigurationProperties("reactive.extension.jetty.shutdown")
public class GracefulShutdownJettyProperties implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(GracefulShutdownJettyProperties.class);

    /**
     * Indicates whether graceful shutdown is enabled or not.
     */
    private boolean enabled;

    /**
     * The number of seconds to wait for active threads to finish before shutting down the embedded web container.
     */
    private Duration timeout = Duration.ofSeconds(60);

    /**
     * The number of seconds to wait before starting the graceful shutdown. During this time, the health checker returns
     * OUT_OF_SERVICE.
     */
    private Duration wait = Duration.ofSeconds(5);

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getWait() {
        return wait;
    }

    public void setWait(Duration wait) {
        this.wait = wait;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "GracefulShutdownJettyProperties[", "]")
                .add("enabled=" + isEnabled())
                .add("timeout=" + getTimeout())
                .add("wait=" + getWait())
                .toString();
    }

    @Override
    public void afterPropertiesSet() {
        EmbeddedContainerInitiation.exclusiveJetty();
        LOG.info(toString());
    }
}

