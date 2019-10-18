package org.yugh.coral.gateway.controller.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.coral.auth.annotation.PreSkipAuth;
import org.yugh.coral.auth.service.AuthService;
import org.yugh.coral.auth.util.JwtHelper;

/**
 * 测试用例token获取
 *
 * @author yugenhai
 */
@Deprecated
@PreSkipAuth
@RestController
public class MockController {


    private final AuthService authService;
    private final JwtHelper jwtHelper;

    @Autowired
    public MockController(AuthService authService, JwtHelper jwtHelper) {
        this.authService = authService;
        this.jwtHelper = jwtHelper;
    }

}
