package org.yugh.coral.boot.rest;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;

/**
 * @author yugenhai
 */
public class CustomUriComponentsBuilder implements UriBuilder {

    @Override
    public UriBuilder scheme(String scheme) {
        return null;
    }

    @Override
    public UriBuilder userInfo(String userInfo) {
        return null;
    }

    @Override
    public UriBuilder host(String host) {
        return null;
    }

    @Override
    public UriBuilder port(int port) {
        return null;
    }

    @Override
    public UriBuilder port(String port) {
        return null;
    }

    @Override
    public UriBuilder path(String path) {
        return null;
    }

    @Override
    public UriBuilder replacePath(String path) {
        return null;
    }

    @Override
    public UriBuilder pathSegment(String... pathSegments) throws IllegalArgumentException {
        return null;
    }

    @Override
    public UriBuilder query(String query) {
        return null;
    }

    @Override
    public UriBuilder replaceQuery(String query) {
        return null;
    }

    @Override
    public UriBuilder queryParam(String name, Object... values) {
        return null;
    }

    @Override
    public UriBuilder queryParams(MultiValueMap<String, String> params) {
        return null;
    }

    @Override
    public UriBuilder replaceQueryParam(String name, Object... values) {
        return null;
    }

    @Override
    public UriBuilder replaceQueryParams(MultiValueMap<String, String> params) {
        return null;
    }

    @Override
    public UriBuilder fragment(String fragment) {
        return null;
    }

    @Override
    public URI build(Object... uriVariables) {
        return null;
    }

    @Override
    public URI build(Map<String, ?> uriVariables) {
        return null;
    }
}
