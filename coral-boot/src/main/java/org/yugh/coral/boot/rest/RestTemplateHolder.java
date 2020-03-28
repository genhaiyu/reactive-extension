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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * @author yugenhai
 */
public class RestTemplateHolder extends RestTemplate {

    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateHolder.class);

    private final RestTemplate restTemplate;

    public RestTemplateHolder(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.errorHandler(new DefaultResponseErrorHandler()).build();
    }


    /**
     * Http Get, Header and Params
     *
     * @param params
     * @param url
     * @param headers
     * @return
     */
    public ResponseEntity getForHeaderParams(Map<String, Object> params, String url, HttpHeaders headers) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(null, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<String> responseEntity;
        if (!params.isEmpty()) {
            params.entrySet().forEach(entry -> builder.queryParam(entry.getKey(), entry.getValue()));
            responseEntity = restTemplate.exchange(builder.build().toString(), HttpMethod.GET, requestEntity, String.class);
        } else {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        }
        LOG.info(String.format("getForHeaderParams url %s header %s request %s response %s", url, headers, requestEntity, responseEntity));
        return responseEntity;
    }


    /**
     * Http Post, Header and Params
     *
     * @param params
     * @param url
     * @param headers
     * @return
     */
    public ResponseEntity postForHeaderParams(Map<String, Object> params, String url, HttpHeaders headers) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, new HttpEntity<>(params, headers), String.class);
        return responseEntity;
    }
}
