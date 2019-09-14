package org.yugh.goodsstock.optimisticlock;

import org.junit.Test;
import org.yugh.goodsstock.GoodsStockApplicationTests;
import org.yugh.goodsstock.optimisticlock.repository.OptimisticLockRepository;
import org.yugh.goodsstock.optimisticlock.vo.StockInfo;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: YuGenHai
 * @name: OptimicticLockTest
 * @creation: 2018/12/3 16:12
 * @notes: 乐观锁测试秒杀商品
 */
public class OptimisticLockTest extends GoodsStockApplicationTests {

    @Resource
    OptimisticLockRepository optimisticLockRepository;

    /**
     * STOCK库存总数，测试可以理解为购买者
     * 表里的stock对应库存
     */
    private static final int STOCK = 10000;

    /**
     * 乐观锁测试秒杀商品库存
     */
    @Test
    public void optimisticLockTest() {
        long beTime = System.currentTimeMillis();
        for (int i = 0; i <= STOCK; i++) {
            StockInfo stockInfo = optimisticLockRepository.queryGoodsInfo();
            if (null != stockInfo) {
                if (stockInfo.getStock() > 0) {
                    //第二次时间
                    Date tokenTime = optimisticLockRepository.queryTokenTime();
                    if (tokenTime.equals(stockInfo.getTokenTime())) {
                        optimisticLockRepository.updateStock();
                        System.out.println(new Thread().getName() + " 抢到了第 " + (i + 1) + " 个商品！");
                    } else {
                        System.err.println(new Thread().getName() + " 抢购第 " + (i + 1) + " 个商品失败，请重新抢购！");
                    }

                } else {
                    System.err.println(new Thread().getName() + " 抱歉，商品没有库存了！");
                }
            }
        }
        System.out.println("秒杀 " + STOCK + " 件商品使用乐观锁需要花费时间：" + (System.currentTimeMillis() - beTime));
    }

}
