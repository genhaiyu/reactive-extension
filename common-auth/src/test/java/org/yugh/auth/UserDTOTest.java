package org.yugh.auth;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.yugh.auth.pojo.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * //
 *
 * @author 余根海
 * @creation 2019-07-16 15:10
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
public class UserDTOTest {

    @Autowired
    private WebTestClient webClient;


    @LocalServerPort
    private int port = 8709;

    private static Map<String, UserDTO> userMap = new HashMap(16);

    @BeforeClass
    public static void setup() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("yugh");
        userDTO.setAliasName("余根海");
        userDTO.setEmail("yugenhai@xx.com");
        userDTO.setSex("1");
        userDTO.setCompany("2");
        userDTO.setDepart("3");
        userDTO.setAdmin(4);
        userDTO.setDisabled(5);
        userMap.put("userInfo", userDTO);
    }

    @Test
    public void testUpdate() throws Exception {
        /** User user = webClient.post().uri("/user/syncUser", port)
         .contentType(MediaType.APPLICATION_JSON)
         .body(BodyInserters.fromObject(userMap.get("userInfo")))
         .exchange()
         .expectStatus().isOk()
         .expectBody(User.class).returnResult().getResponseBody();
         Assert.assertNotNull(user);*/
    }


    @Test
    public void testAdd() throws Exception {
        /** User user = webClient.post().uri("/user/addUser")
         .contentType(MediaType.APPLICATION_JSON)
         .body(BodyInserters.fromObject(userMap.get("userInfo")))
         .exchange()
         .expectStatus().isOk()
         .expectBody(User.class).returnResult().getResponseBody();
         Assert.assertNotNull(user);*/
    }

}
