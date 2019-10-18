package org.yugh.coral.locks.optimisticlock.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.yugh.coral.locks.optimisticlock.vo.StockInfo;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: YuGenHai
 * @name: OptimisticLockRepository
 * @creation: 2018/12/3 14:17
 * @notes: 乐观锁主要思想是每次大家都拿一次时间，谁优先操作到了，修改时间
 * 下个人来操作时，就校验不通过，需要重新拿一次时间
 */
@Repository
public class OptimisticLockRepository {

    /**
     * 测试使用 {@link JdbcTemplate}
     */
    @Resource
    private JdbcTemplate jdbcTemplate;


    /**
     * 乐观锁令牌获取
     *
     * @return
     * @author yugenhai
     */
    public StockInfo queryGoodsInfo() {
        //获取库存和时间戳
        String sql = "select id,name,stock,version,token_time from goods where id=1";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        StockInfo stockInfo = new StockInfo();
        if (null != list && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            stockInfo.setStock(Integer.valueOf(String.valueOf(map.get("stock"))));
            try {
                stockInfo.setTokenTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(map.get("token_time"))));
            } catch (Exception e) {
                System.out.println("乐观锁时间转换异常！" + e.getMessage());
                return null;
            }
        }
        return stockInfo;
    }

    /**
     * 获取时间戳
     *
     * @return
     * @author yugenhai
     */
    public Date queryTokenTime() {
        String sql = "select id,name,stock,version,token_time from goods where id=1";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (null != list && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(map.get("token_time")));
            } catch (Exception e) {
                System.out.println("乐观锁时间转换异常！" + e.getMessage());
            }
        }
        return null;
    }


    /**
     * 只做秒杀减库存操作
     *
     * @return
     * @author yugenhai
     */
    public int updateStock() {
        String update = "update goods set stock=stock-1,token_time=now() where id=1";
        jdbcTemplate.update(update);
        return 1;
    }
}
