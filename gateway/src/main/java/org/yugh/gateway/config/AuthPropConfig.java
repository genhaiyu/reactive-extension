package org.yugh.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * //配置过滤的服务和地址
 *
 * @author: 余根海
 * @creation: 2019-06-24 14:34
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "auth-props")
public class AuthPropConfig {
    /**
     * 服务
     */
    private Map<String, Integer> apiUrlMap;
    /**
     * 地址
     */
    private List<String> excludeUrls;
    private String accessToken;
    private String accessIp;
    private String authLevel;
}
