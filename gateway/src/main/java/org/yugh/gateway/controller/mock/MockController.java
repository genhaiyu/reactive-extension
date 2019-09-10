package org.yugh.gateway.controller.mock;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.auth.annotation.PreSkipAuth;
import org.yugh.auth.pojo.dto.UserDTO;
import org.yugh.auth.service.AuthService;
import org.yugh.auth.util.JwtHelper;
import org.yugh.auth.util.StringPool;

import java.util.Map;

/**
 * 测试用例token获取
 *
 * @author yugenhai
 */
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


    /**
     * Mock 从 BeforeClass通过cookie获取生成的token
     * <p>
     * 例如 String token = webClient.post().uri("/gettoken")....
     *
     * @param cookieValue
     * @return String
     */
    @GetMapping("gettoken")
    public String generatedTokenByGateway(String cookieValue) {
        Assert.hasText(cookieValue, "param not contain valid text content");
        boolean isLogin = authService.isLoginByMock(cookieValue);
        try {
            if (isLogin) {
                UserDTO user = authService.getUserByMock(cookieValue);
                Map<String, Object> userMap = Maps.newHashMap();
                String dataWorksUserInfo = StringPool.DATAWORKS_USER_INFO;
                userMap.put(dataWorksUserInfo, user);
                return jwtHelper.generateToken(dataWorksUserInfo, userMap);
            }
        } catch (Exception e) {
            throw new RuntimeException("generatedTokenByGateway is Exception!");
        }
        return "GUAZISSO is Invalid";
    }
}
