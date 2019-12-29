package org.yugh.coral.boot.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.yugh.coral.boot.handler.RestTemplateResponseErrorHandler;
import org.yugh.coral.core.utils.JsonUtils;

import java.util.Map;

/**
 * @author yugenhai
 */
@Slf4j
public class RestTemplateHolder extends RestTemplate {

    private final RestTemplate restTemplate;

    public RestTemplateHolder(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }


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
        log.info("\n getForHeaderParams | url is {},header:{}, request:{}, response:{}", url, JsonUtils.serializeToJson(requestEntity), JsonUtils.serializeToJson(params), JsonUtils.serializeToJson(responseEntity));
        return responseEntity;
    }


    public ResponseEntity postForHeaderParams(Map<String, Object> params, String url, HttpHeaders headers){
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, new HttpEntity<>(params, headers), String.class);
        return responseEntity;
    }
}
