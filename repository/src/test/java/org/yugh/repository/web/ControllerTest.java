package org.yugh.repository.web;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.yugh.common.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * //
 *
 * @author 余根海
 * @creation 2019-07-16 15:10
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private WebTestClient webClient;


    @LocalServerPort
    private int port = 6301;

    private static Map<String, User> userMap = new HashMap<>();

    @BeforeClass
    public static void setup() throws Exception {

        User user = new User();
        user.setUserName("yugh");
        user.setAliasName("余根海");
        user.setEmail("yugenhai@guazi.com");
        user.setSex("1");
        user.setCompany("2");
        user.setDepart("3");
        user.setAdmin(4);
        user.setDisabled(5);


        userMap.put("userInfo", user);
    }

    @Test
    public void testUpdate() throws Exception {

        User user = webClient.post().uri("/user/syncUser", port)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userMap.get("userInfo")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).returnResult().getResponseBody();

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), userMap.get("userInfo").getId());
    }



    @Test
    public void testAdd() throws Exception{
        User user = webClient.post().uri("/user/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userMap.get("userInfo")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).returnResult().getResponseBody();

        Assert.assertNotNull(user);
    }

}
