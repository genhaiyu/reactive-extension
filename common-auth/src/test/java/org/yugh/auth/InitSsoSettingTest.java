package org.yugh.auth;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.yugh.auth.base.TestAbstract;
import org.yugh.auth.service.AuthService;
import org.yugh.auth.util.SSOConfig;

/**
 * @author yugenhai
 */
public class InitSsoSettingTest extends TestAbstract {

    @Autowired
    private SSOConfig properties;

    @Value("${sso.test.url}")
    private String ssoTestUrl;

    @Autowired
    private AuthService authService;

    @Test
    public void test1() {

        //  List result = properties.getIgnoreMethods();
        // System.out.println(result);

        String string = properties.getSsoProdAppKey();

        System.out.println(string);

    }


    @Test
    public void test2() {

        System.out.println(ssoTestUrl);

    }


}
