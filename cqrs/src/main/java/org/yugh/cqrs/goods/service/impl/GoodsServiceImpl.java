package org.yugh.cqrs.goods.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yugh.cqrs.goods.entites.GoodsEntity;
import org.yugh.cqrs.goods.entites.QGoodsEntity;
import org.yugh.cqrs.goods.repositories.GoodsRepository;
import org.yugh.cqrs.goods.service.GoodsService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author yugenhai
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class GoodsServiceImpl implements GoodsService {


    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private GoodsRepository goodsRepository;


    @Override
    public GoodsEntity addGoods(GoodsEntity goods) {
        return goodsRepository.save(goods);
    }

    @Override
    public GoodsEntity getGoods(String name) {
        final JPAQuery<GoodsEntity> jpaQuery = new JPAQuery(manager);
        final QGoodsEntity qGoodsEntity = QGoodsEntity.goodsEntity;
        return jpaQuery.from(qGoodsEntity).where(qGoodsEntity.name.eq(name)).fetchOne();
    }
}
