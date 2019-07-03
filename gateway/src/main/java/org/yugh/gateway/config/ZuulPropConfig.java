package org.yugh.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * //路由拦截配置
 *
 * @author: 余根海
 * @creation: 2019-07-02 19:43
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "zuul")
public class ZuulPropConfig {
    /**
     * 服务
     */
    private Map<String, String> routes;


}
