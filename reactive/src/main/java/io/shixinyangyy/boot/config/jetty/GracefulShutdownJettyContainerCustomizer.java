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
 */


package io.shixinyangyy.boot.config.jetty;

import org.springframework.boot.web.embedded.jetty.JettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * Custom container connector.
 *
 * @author Shixinyangyy
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
