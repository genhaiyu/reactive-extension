package org.yugh.gatewaynew.properties;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * //白名单和黑名单属性配置
 *
 * @author  余根海
 * @creation  2019-07-05 15:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
@Slf4j
@Component
@Configuration
@ConfigurationProperties(prefix = "auth-skip")
public class AuthSkipUrlsProperties implements InitializingBean {

    private static final String NORMAL = "(\\w|\\d|-)+";
    private List<Pattern> urlPatterns = new ArrayList(10);
    private List<Pattern> serverPatterns = new ArrayList(10);
    private List<String> instanceServers;
    private List<String> apiUrls;

    @Override
    public void afterPropertiesSet() {
        instanceServers.stream().map(d -> d.replace("*", NORMAL)).map(Pattern::compile).forEach(serverPatterns::add);
        apiUrls.stream().map(s -> s.replace("*", NORMAL)).map(Pattern::compile).forEach(urlPatterns::add);
        log.info("============> 配置服务器ID : {} , 白名单Url : {}", serverPatterns, urlPatterns);
    }

}

