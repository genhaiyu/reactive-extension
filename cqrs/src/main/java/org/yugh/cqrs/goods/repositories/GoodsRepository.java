package org.yugh.cqrs.goods.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.yugh.cqrs.goods.entites.GoodsEntity;
import org.yugh.cqrs.goods.entites.QGoodsEntity;
import org.yugh.cqrs.jpa.ddd.BaseRepository;

/**
 * @author yugenhai
 */
public interface GoodsRepository extends BaseRepository<GoodsEntity, Long>, QuerydslPredicateExecutor<QGoodsEntity> {

    @Modifying
    @Query(value = "update GoodsEntity g set g.stock = :#{#goodsEntity.stock}," +
            " g.tokenTime = :#{#goodsEntity.tokenTime} where g.name= :#{#goodsEntity.name}")
    void updateGoods(@Param("goodsEntity") GoodsEntity goodsEntity);
}
