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


package io.genhai.boot.config.embedded;

import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;

/**
 * Tomcat container check, and to exclude
 *
 * @author yugenhai
 */
public class WebServiceContainerMatcher implements Ordered {

    private static final String STARTUP_TOMCAT = "org.apache.catalina.startup.Tomcat";
    private int order = Ordered.HIGHEST_PRECEDENCE + 1;

    public static void embeddedContainerMatcher() {
        if (ClassUtils.isPresent(STARTUP_TOMCAT, null)) {
            throw new IllegalArgumentException("The project does not support the Tomcat.");
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
