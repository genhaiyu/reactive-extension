package org.yugh.coral.boot.rest;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.web.client.RestTemplate;

/**
 * @author yugenhai
 */
public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {

    /**
     *
     * @param restTemplate
     */
    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
    }
}
