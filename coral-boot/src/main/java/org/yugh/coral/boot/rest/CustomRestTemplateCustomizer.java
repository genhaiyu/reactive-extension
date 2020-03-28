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


package org.yugh.coral.boot.rest;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.web.client.RestTemplate;

/**
 * @author yugenhai
 */
public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new HttpRequestInterceptor());
    }
}
