package org.yugh.cqrs.goods.business;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yugh.cqrs.goods.command.CommandHandler;
import org.yugh.cqrs.goods.entites.GoodsEntity;
import org.yugh.cqrs.goods.model.GoodsModel;
import org.yugh.cqrs.goods.service.GoodsService;

/**
 * 创建商品
 *
 * @author yugenhai
 */
public class AddGoodsCommandHandler implements CommandHandler<GoodsModel> {


    @Autowired
    private GoodsService goodsService;


    @Override
    public Object execute(GoodsModel commandModel) {
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(commandModel, goodsEntity);
        return goodsService.addGoods(goodsEntity);
    }
}
