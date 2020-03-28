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


package org.yugh.coral.core.config.embedded;

import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;

/**
 * Exclude tomcat
 *
 * @author yugenhai
 */
public class WebServiceContainerMatcher implements Ordered {

    private static final String STARTUP_TOMCAT = "org.apache.catalina.startup.Tomcat";
    private static final String CORAL_JETTY_CONFIG = "coral.jetty.config";
    private int order = Ordered.HIGHEST_PRECEDENCE + 1;

    public static void embeddedContainerMatcher() {
        if (ClassUtils.isPresent(STARTUP_TOMCAT, null)) {
           // Boolean enabled = environment.getProperty(CORAL_JETTY_CONFIG, Boolean.class);
            throw new ExceptionInInitializerError("Please remove Tomcat support Jar !");
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
