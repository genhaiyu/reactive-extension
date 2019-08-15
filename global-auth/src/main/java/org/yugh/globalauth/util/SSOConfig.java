package org.yugh.globalauth.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Gateway check env
 *
 * @author yugenhai
 */
@Data
@Component
public class SSOConfig {

    /**
     * Test env
     */
    @Value("${sso.test.url}")
    private String ssoTestUrl;
    @Value("${sso.test.appKey}")
    private String ssoTestAppKey;
    @Value("${sso.test.appSecret}")
    private String ssoTestAppSecret;
    @Value("${sso.test.identity}")
    private String ssoTestIdentity;
    @Value("${sso.test.logout}")
    private String ssoTestLogout;

    /**
     * Pro env
     */
    @Value("${sso.pro.url}")
    private String ssoProUrl;
    @Value("${sso.pro.appKey}")
    private String ssoProAppKey;
    @Value("${sso.pro.appSecret}")
    private String ssoProAppSecret;
    @Value("${sso.pro.identity}")
    private String ssoProIdentity;
    @Value("${sso.pro.logout}")
    private String ssoProLogout;
}
