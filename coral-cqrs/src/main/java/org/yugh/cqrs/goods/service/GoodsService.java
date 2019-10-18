package org.yugh.cqrs.goods.service;

import org.yugh.cqrs.goods.entites.GoodsEntity;

/**
 * @author yugenhai
 */
public interface GoodsService {


    /**
     * 添加一个商品
     *
     * @param goods
     * @return
     */
    GoodsEntity addGoods(GoodsEntity goods);


    /**
     * 获得一个商品
     *
     * @param name
     * @return
     */
    GoodsEntity getGoods(String name);
}
