/*
 * Copyright (c) 2019-2029, yugenhai108@gmail.com.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yugh.coral.boot.rest;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;

/**
 * @author yugenhai
 */
@Deprecated
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
