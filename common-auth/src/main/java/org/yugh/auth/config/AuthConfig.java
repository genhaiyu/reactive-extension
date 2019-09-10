package org.yugh.auth.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.yugh.auth.util.JwtHelper;

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
@Configuration
@Setter(AccessLevel.PRIVATE)
public class AuthConfig {

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
    @Value("${prod.sso.web}")
    private String prodSsoWeb;
    @Value("${prod.staff.dns}")
    private String prodStaff;
    @Value("${prod.sso.corp}")
    private String prodSsoCorp;
    /**
     * Test Host
     */
    @Value("${test.sso.web}")
    private String testSsoWeb;
    @Value("${test.stage.corp}")
    private String testStageCorp;
    @Value("${test.staff.dev}")
    private String testStaffDev;

    /**
     * # Logout Domain
     */
    @Value("${gateway.cloud}")
    private String gatewayCloud;
    @Value("${gateway.apps}")
    private String gatewayApps;
    @Value("${xx.corp}")
    private String xxCorp;
    @Value("${xx.apps}")
    private String xxApps;
    @Value("${xx.cloud}")
    private String xxCloud;
    @Value("${xxxx.com}")
    private String xxCom;
    @Value("${env.switch}")
    private String envSwitch;
    @Value("${shutdown.client}")
    private String shutdownClient;

    /**
     * JWT
     */
    @Value("${jwt.token.secret}")
    private String jwtTokenSecret;
    @Value("${jwt.token.expiration}")
    private Long jwtTokenExpiration;

    @Bean
    public JwtHelper jwtHelper() {
        return new JwtHelper(jwtTokenSecret, jwtTokenExpiration);
    }

}
