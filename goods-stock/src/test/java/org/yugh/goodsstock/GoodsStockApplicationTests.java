package org.yugh.goodsstock;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GoodsStockApplication.class)
@TestPropertySource({
		"classpath:/application.properties"
})
public abstract class GoodsStockApplicationTests {


}
