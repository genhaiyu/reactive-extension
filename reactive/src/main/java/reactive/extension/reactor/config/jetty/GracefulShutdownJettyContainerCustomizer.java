package reactive.extension.reactor.config.jetty;

import org.springframework.boot.web.embedded.jetty.JettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * Server of the Jetty properties and configuration.
 *
 * @author Genhai Yu
 */
public class GracefulShutdownJettyContainerCustomizer implements WebServerFactoryCustomizer<JettyReactiveWebServerFactory> {

    private final GracefulShutdownJettyConnectorCustomizer gracefulShutdownJettyConnectorCustomizer;

    public GracefulShutdownJettyContainerCustomizer(GracefulShutdownJettyConnectorCustomizer connectorCustomizer) {
        this.gracefulShutdownJettyConnectorCustomizer = connectorCustomizer;
    }

    @Override
    public void customize(JettyReactiveWebServerFactory factory) {
        factory.addServerCustomizers(gracefulShutdownJettyConnectorCustomizer);
    }
}
