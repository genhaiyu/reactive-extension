package org.yugh.auth.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Gateway check env
 * <p>
 * * xx-corp.com
 * * xx.com
 * * xx-apps.com
 * * xx.com
 * * <p>
 * * xx-cloud.com
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
    @Value("${sso.prod.url}")
    private String ssoProdUrl;
    @Value("${sso.prod.appKey}")
    private String ssoProdAppKey;
    @Value("${sso.prod.appSecret}")
    private String ssoProdAppSecret;
    @Value("${sso.prod.identity}")
    private String ssoProdIdentity;
    @Value("${sso.prod.logout}")
    private String ssoProdLogout;
    /**
     * Pro Host
     */
    private String prodSsoWeb = "xx.com";
    private String prodStaff = "xx.com";
    private String prodSsoCorp = "xx.com";
    /**
     * Test Host
     */
    private String testSsoWeb = "xx.com";
    private String testStageCorp = "xx.com";
    private String testStaffDev = "xx.com";


}
