package org.yugh.coral.auth;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yugh.coral.auth.base.TestAbstract;
import org.yugh.coral.auth.config.AuthConfig;
import org.yugh.coral.auth.service.AuthService;

/**
 * @author yugenhai
 */
public class InitSsoSettingTest extends TestAbstract {

    @Autowired
    private AuthConfig authConfig;


    @Autowired
    private AuthService authService;

    @Test
    public void test1() {

        //  List result = properties.getIgnoreMethods();
        // System.out.println(result);

        String string = authConfig.getSsoProdAppKey();

        System.out.println(string);

    }



    @Test
    public void test2() {


    }


}
