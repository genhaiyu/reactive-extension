package org.yugh.coral.gateway.properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 余根海
 * @creation 2019-07-05 15:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Component
@ConfigurationProperties(prefix = "auth-skip")
public class AuthSkipUrlsProperties implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(AuthSkipUrlsProperties.class);
    private static final String NORMAL = "(\\w|\\d|-)+";
    private List<Pattern> urlPatterns = new ArrayList<>(10);
    private List<Pattern> serverPatterns = new ArrayList<>(10);
    private List<String> dataWorksServers;
    private List<String> apiUrls;

    public List<Pattern> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<Pattern> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public List<Pattern> getServerPatterns() {
        return serverPatterns;
    }

    public void setServerPatterns(List<Pattern> serverPatterns) {
        this.serverPatterns = serverPatterns;
    }

    public List<String> getDataWorksServers() {
        return dataWorksServers;
    }

    public void setDataWorksServers(List<String> dataWorksServers) {
        this.dataWorksServers = dataWorksServers;
    }

    public List<String> getApiUrls() {
        return apiUrls;
    }

    public void setApiUrls(List<String> apiUrls) {
        this.apiUrls = apiUrls;
    }

    /**
     * This {@link AuthSkipUrlsProperties} init
     */
    @Override
    public void afterPropertiesSet() {
        dataWorksServers.stream().map(d -> d.replace("*", NORMAL)).map(Pattern::compile).forEach(serverPatterns::add);
        apiUrls.stream().map(s -> s.replace("*", NORMAL)).map(Pattern::compile).forEach(urlPatterns::add);
        LOG.info("============> Configuration Instance IDs : {} , Skip Urls : {}", serverPatterns, urlPatterns);
    }
}

