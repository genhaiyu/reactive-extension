package org.yugh.coral.boot.config.jetty;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * see https://github.com/spring-projects/spring-boot/issues/4657
 *
 * @author yugenhai
 */
@Slf4j
public class GracefulShutdownJettyServer implements ApplicationListener<ContextClosedEvent> {

    @Setter
    private static Server server;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (server == null) {
            log.error("Jetty server variable is null, this should not happen!");
            return;
        }
        log.info("Entering shutdown for Jetty.");
        if (!(server.getHandler() instanceof StatisticsHandler)) {
            log.error("Root handler is not StatisticsHandler, graceful shutdown may not work at all!");
        } else {
            log.info("Active requests: " + ((StatisticsHandler) server.getHandler()).getRequestsActive());
        }
        try {
            long begin = System.currentTimeMillis();
            server.stop();
            log.info("Shutdown took " + (System.currentTimeMillis() - begin) + " ms.");
        } catch (Exception e) {
            log.error("Fail to shutdown gracefully.", e);
        }
    }
}
