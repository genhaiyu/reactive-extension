package org.yugh.coral.client.base;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.yugh.coral.client.ClientApplication;

/**
 * @author yugenhai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClientApplication.class)
@TestPropertySource({
        "classpath:/application.yml"
})
public abstract class AbstractTest {


    @LocalServerPort
    private int port = 7072;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeClass
    public static void setup() throws Exception {

    }


    @Test
    public void testUpdate() throws Exception {
    }


    @Test
    public void testAdd() throws Exception {

    }
}
