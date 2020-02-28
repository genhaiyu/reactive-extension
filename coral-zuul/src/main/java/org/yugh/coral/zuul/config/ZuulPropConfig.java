package org.yugh.coral.zuul.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * //路由拦截配置
 *
 * @author: 余根海
 * @creation: 2019-07-02 19:43
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "auth-props")
public class ZuulPropConfig implements InitializingBean {

    private static final String normal = "(\\w|\\d|-)+";
    private List<Pattern> patterns = new ArrayList<>();
    private Map<String, String> apiUrlMap;
    private List<String> excludeUrls;
    private String accessToken;
    private String accessIp;
    private String authLevel;

    @Override
    public void afterPropertiesSet() throws Exception {
        excludeUrls.stream().map(s -> s.replace("*", normal)).map(Pattern::compile).forEach(patterns::add);
        log.info("============> 配置的白名单Url:{}", patterns);
    }


}
