package org.yugh.coral.boot.config.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.lang.Nullable;

/**
 * <a href="https://github.com/spring-projects/spring-boot/issues/4657</a>.
 *
 * @author yugenhai
 */
@Deprecated
public class GracefulShutdownJettyServer implements ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(GracefulShutdownJettyServer.class);

    private static Server server;

    public static void setServer(Server server) {
        GracefulShutdownJettyServer.server = server;
    }

    @Override
    public void onApplicationEvent(@Nullable ContextClosedEvent event) {
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
