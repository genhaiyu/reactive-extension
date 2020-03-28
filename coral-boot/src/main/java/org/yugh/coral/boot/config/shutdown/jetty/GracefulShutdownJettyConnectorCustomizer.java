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

package org.yugh.coral.boot.config.shutdown.jetty;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;

/**
 * @author yugenhai
 */
public class GracefulShutdownJettyConnectorCustomizer implements JettyServerCustomizer {


    private static final Logger LOG = LoggerFactory.getLogger(GracefulShutdownJettyConnectorCustomizer.class);

    private static final int CHECK_INTERVAL = 10;

   // private final ApplicationContext applicationContext;

    private Server server;


    @Override
    public void customize(Server server) {
        this.server = server;
    }

}
