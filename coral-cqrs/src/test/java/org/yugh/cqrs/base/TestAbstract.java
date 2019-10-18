package org.yugh.cqrs.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yugh.cqrs.CqrsApplication;

/**
 * @author yugenhai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CqrsApplication.class)
@TestPropertySource({
        "classpath:application.properties", "classpath:application.yml"})
public class TestAbstract {


}
