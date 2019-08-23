package org.yugh.auth.base;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yugh.auth.DataWorksAuthApplication;

/**
 * @author yugenhai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataWorksAuthApplication.class)
@TestPropertySource({
        "classpath:/application.properties"
})
public abstract class TestAbstract {

    //test

    /**
     * @Autowired private WebTestClient webClient;
     * @LocalServerPort private int port = 6301;
     * <p>
     * private static Map<String, User> userMap = new HashMap(16);
     */
    @BeforeClass
    public static void setup() throws Exception {

        /**    User user = new User();
         user.setUserName("yugh");
         user.setAliasName("余根海");
         user.setEmail("yugenhai@xx.com");
         user.setSex("1");
         user.setCompany("2");
         user.setDepart("3");
         user.setAdmin(4);
         user.setDisabled(5);

         userMap.put("userInfo", user);*/
    }


    @Test
    public void testUpdate() throws Exception {
        /**User user = webClient.post().uri("/user/XX", port)
         .contentType(MediaType.APPLICATION_JSON)
         .body(BodyInserters.fromObject(userMap.get("userInfo")))
         .exchange()
         .expectStatus().isOk()
         .expectBody(User.class).returnResult().getResponseBody();

         Assert.assertNotNull(user);

         */
    }


    @Test
    public void testAdd() throws Exception {
        /** User user = webClient.post().uri("/user/XX")
         .contentType(MediaType.APPLICATION_JSON)
         .body(BodyInserters.fromObject(userMap.get("userInfo")))
         .exchange()
         .expectStatus().isOk()
         .expectBody(User.class).returnResult().getResponseBody();

         Assert.assertNotNull(user);*/
    }

}
