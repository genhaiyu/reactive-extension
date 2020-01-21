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

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.yugh.coral.boot.handler.CustomRestTemplateResponseErrorHandler;
import org.yugh.coral.core.utils.JsonUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author yugenhai
 */
@Slf4j
public class CustomRestTemplateHolder extends RestTemplate {

    private final RestTemplate restTemplate;

    public CustomRestTemplateHolder(RestTemplateBuilder restTemplateBuilder) {

//        if(null != proxyProperties && proxyProperties.getEnable()){
//            // create ResetTemplate
//        }
        this.restTemplate = restTemplateBuilder.errorHandler(new CustomRestTemplateResponseErrorHandler()).build();
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
        log.info("\n getForHeaderParams | url is {},header:{}, request:{}, response:{}", url, JsonUtils.serializeToJson(requestEntity), JsonUtils.serializeToJson(params), JsonUtils.serializeToJson(responseEntity));
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


    /****************** Business *********************************************************/
    /****************** Business *********************************************************/
    private String proxyBaseURL;
    private Set<String> ignoreDomains;
    private static final String PROXY_HEADER = "target-domain";
    private static final String REQUEST_ID_HEADER = "x-request-id";
    private static final String CLIENT_IP_SOURCE_HEADER = "X-Real-IP";
    private static final String CLIENT_IP_TARGET_HEADER = "x-client-ip";
    private static final Set<String> ADD_USER_IP_DOMAINS = new HashSet<>();


    /****************** Business *********************************************************/
    /****************** Business *********************************************************/


}
