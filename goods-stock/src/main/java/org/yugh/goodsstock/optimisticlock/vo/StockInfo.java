package org.yugh.goodsstock.optimisticlock.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: YuGenHai
 * @name: StockInfo
 * @creation: 2018/12/3 15:48
 * @notes: 乐观锁库存和时间对象
 */
@Data
public class StockInfo implements Serializable {

    private static final long serialVersionUID = 7004965296554163538L;

    /**
     * 库存量和时间戳
     */
    private Integer stock;
    private Date tokenTime;
}
