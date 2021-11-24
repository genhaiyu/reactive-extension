package reactive.extension.reactor.config.jetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Server of the Jetty properties and configuration.
 *
 * @author Genhai Yu
 */
public class GracefulShutdownJettyHealthIndicator implements HealthIndicator {

    private static final Logger LOG = LoggerFactory.getLogger(GracefulShutdownJettyHealthIndicator.class);
    private final ApplicationContext applicationContext;
    private final GracefulShutdownJettyProperties gracefulShutdownJettyProperties;
    private Health health = Health.up().build();

    public GracefulShutdownJettyHealthIndicator(ApplicationContext ctx, GracefulShutdownJettyProperties properties) {
        this.applicationContext = ctx;
        this.gracefulShutdownJettyProperties = properties;
    }

    @Override
    public Health health() {
        return health;
    }

    @EventListener(ContextClosedEvent.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void contextClosed(ContextClosedEvent event) throws InterruptedException {
        if (isEventFromLocalContext(event)) {
            updateHealthToOutOfService();
            increaseTheBufferTime();
        }
    }

    private void updateHealthToOutOfService() {
        // update out of service
        health = Health.outOfService().build();
        LOG.info("Health status set to out of service");
    }

    private void increaseTheBufferTime() throws InterruptedException {
        // Increase the buffer time by another 5 or 10 seconds
        LOG.info("Increase {} seconds the buffer time", gracefulShutdownJettyProperties.getWait().getSeconds());
        Thread.sleep(gracefulShutdownJettyProperties.getWait().toMillis());
    }

    private boolean isEventFromLocalContext(ContextClosedEvent event) {
        return event.getApplicationContext().equals(applicationContext);
    }
}
