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
import org.yugh.repository.entites.UserEntity;

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

    private static Map<String, UserEntity> userMap = new HashMap<>();

    @BeforeClass
    public static void setup() throws Exception {

        UserEntity user = new UserEntity();
        user.setName("yugh");
        user.setPhone("17611255755");

        userMap.put("userInfo", user);
    }

    @Test
    public void testUpdate() throws Exception {

        UserEntity user = webClient.post().uri("/user/syncUser", port)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userMap.get("userInfo")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserEntity.class).returnResult().getResponseBody();

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), userMap.get("userInfo").getId());
    }


    @Test
    public void testAdd() throws Exception {
        UserEntity user = webClient.post().uri("/user/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userMap.get("userInfo")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserEntity.class).returnResult().getResponseBody();

        Assert.assertNotNull(user);
    }

}
