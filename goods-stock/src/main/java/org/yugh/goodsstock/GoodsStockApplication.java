package org.yugh.goodsstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试类在src/main/test/java/**
 * <p>
 * 单机秒杀商品库存
 * <p>
 * <p>
 * FIXME 乐观锁的时间存在并发不安全，在设计上也不安全，需要修改为unix的时间版本来对比
 * Modify 2019-09-11 {@link org.yugh.goodsstock.optimisticlock.repository.OptimisticLockRepository}
 * <p>
 * 若修改可参照 {@link `repository` InstantLongConverter} code {convertToDatabaseColumn}
 * <p>
 * Like this 例如 :
 * <p>
 * InstantLongConverter converter = new InstantLongConverter();
 * System.out.println(converter.convertToDatabaseColumn(Instant.now()));
 *
 * @author yugenhai
 */
@SpringBootApplication
public class GoodsStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsStockApplication.class, args);
    }
}