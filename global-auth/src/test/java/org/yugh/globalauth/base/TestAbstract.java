package org.yugh.globalauth.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yugh.globalauth.DataWorksAuthApplication;

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

}
