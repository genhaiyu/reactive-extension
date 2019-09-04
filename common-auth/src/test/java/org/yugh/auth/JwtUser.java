package org.yugh.auth;

import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yugh.auth.base.TestAbstract;
import org.yugh.auth.util.JwtHelper;
import org.yugh.auth.util.StringPool;

import java.util.Map;

/**
 * @author yugenhai
 */
public class JwtUser extends TestAbstract {


    @Autowired
    private JwtHelper jwtHelper;


    @Test
    public void generateToken() {
        String userInfo = "yugenhai";
        Map<String, Object> userMap = Maps.newHashMap();
        userMap.put("userInfo", userInfo);
        String token = jwtHelper.generateToken(StringPool.DATAWORKS_USER_INFO, userMap);
        System.out.println("加密后的token " + token);
    }

    @Test
    public void getSubject() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySW5mbyI6Inl1Z2VuaGFpIiwic3ViIjoidXNlckluZm8iLCJleHAiOjE1Njc1NjQ5MDAsImlhdCI6MTU2NzUwMTQwMH0.9iCOOufJy-Z6grGysZzyVI68YcU5gQYchBxpB6ikRkE-loJ6ijJtoS23TFKYGPrfM9gch-Bi7Kiwch314HphJg";
        String subject = jwtHelper.getSubject(token);

        System.out.println(subject);

        Claims claims = jwtHelper.getAllClaimsFromToken(token);

        System.out.println(claims);

    }


    @Test
    public void validateToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySW5mbyI6Inl1Z2VuaGFpIiwic3ViIjoidXNlckluZm8iLCJleHAiOjE1Njc1NjQ5MDAsImlhdCI6MTU2NzUwMTQwMH0.9iCOOufJy-Z6grGysZzyVI68YcU5gQYchBxpB6ikRkE-loJ6ijJtoS23TFKYGPrfM9gch-Bi7Kiwch314HphJg";

        boolean test = jwtHelper.validateToken(token);
        System.out.println(test);

    }
}
