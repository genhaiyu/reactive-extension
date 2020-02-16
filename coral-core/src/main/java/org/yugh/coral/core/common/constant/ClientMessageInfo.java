package org.yugh.coral.core.common.constant;

/**
 * @author yugenhai
 */
public interface ClientMessageInfo {

    String INSTANCE_IP                  = "127.0.0.1";
    String SERVLET_REQUEST_ENABLE       = "coeal.servlet.request.enabled";
    String REACTIVE_REQUEST_ENABLE      = "coeal.reactive.request.enabled";
    String JETTY_CONTAINER_CONFIG       = "coeal.jetty.config.enabled";
    String REACTOR_CONTAINER_CONFIG     = "coeal.reactor.config.enabled";
    String TOMCAT_CONTAINER_CONFIG      = "coeal.tomcat.config.enabled";
    String UNDERTOW_CONTAINER_CONFIG    = "coeal.undertow.config.enabled";
    String AUTHORIZATION                = "Authorization";
    // String CONTAINER_AUTO_CONFIG        = "coral.container.config.?";
}
