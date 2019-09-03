package org.yugh.goodsstock.pessimisticlock.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: YuGenHai
 * @name: SeckRepository
 * @creation: 2018/11/28 00:30
 * @notes: 悲观锁DAO层
 * @notes: 悲观锁需要注意mysql自带自动commit，用行锁需要开启事务 set transation 或者set autocommit =0
 * 防止自动提交，set autocommit =1 自动提交
 */
@Repository
public class PessimisticLockRepository {

    /**
     * 测试使用 {@link JdbcTemplate}
     */
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取现有库存量
     * @return
     * @author yugenhai
     */
    public int queryStock() {
        //开启事务
        String lock = "set autocommit=0";
        jdbcTemplate.update(lock);
        //获得当前库存 并上锁
        String sql = "select id,name,stock,version,token_time from goods where id=1 for update";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if(null != list && list.size() > 0){
            Map<String,Object> map = list.get(0);
            System.out.println("当前库存值： "+ map.get("stock"));
            return Integer.valueOf(String.valueOf(map.get("stock")));
        }
        return 0;
    }

    /**
     * 还有库存量，并且要释放当前锁
     * @author yugenhai
     * @return
     */
    public int updateStock() {
        String update = "update goods set stock=stock-1 where id=1";
        jdbcTemplate.update(update);
        String unlock = "commit";
        jdbcTemplate.update(unlock);
        return 1;
    }

    /**
     * 商品被抢光后需要释放
     * @author yugenhai
     * @return
     */
    public int unlock(){
        String unlock = "commit";
        jdbcTemplate.update(unlock);
        return 1;
    }

}
