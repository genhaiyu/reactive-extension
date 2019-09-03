package org.yugh.goodsstock.pessimisticlock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yugh.goodsstock.pessimisticlock.repository.PessimisticLockRepository;

import javax.annotation.Resource;

/**
 * @author: YuGenHai
 * @name: PessimisticLockTest
 * @creation: 2018/11/28 00:32
 * @notes: 悲观锁测试秒杀商品
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PessimisticLockTest {

    @Resource
    PessimisticLockRepository pessimisticLockRepository;

    /**
     * STOCK库存总数，测试可以理解为购买者
     * 表里的stock对应库存
     */
    private static final int STOCK = 1000;

    /**
     * 悲观锁秒杀商品
     * @author yugenhai
     */
    @Test
    public void pessimisticLockTest() {
        long beTime = System.currentTimeMillis();
        for (int i = 0; i <= STOCK; i++) {
            //获得当前库存
            //顺带上锁，开启事务
            int stock = pessimisticLockRepository.queryStock();
            if (stock > 0) {
                //库存还有
                //当前用户继续秒杀一个商品 并提交事务 释放锁
                pessimisticLockRepository.updateStock();
                System.out.println(new Thread().getName() + " 抢到了第 " + (i + 1) + " 商品");
            } else {
                //没有库存后释放锁
                System.err.println(new Thread().getName() + " 抱歉，商品没有库存了！");
                pessimisticLockRepository.unlock();
                break;
            }
        }
        System.out.println("秒杀 "+ STOCK + " 件商品使用悲观锁需要花费时间：" + (System.currentTimeMillis() - beTime));

    }


}
