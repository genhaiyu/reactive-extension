package org.yugh.cqrs.goods.business;

import org.yugh.cqrs.goods.command.CommandHandler;
import org.yugh.cqrs.goods.model.OrderModel;

/**
 * @author yugenhai
 */
public class AddOrderCommandHandler implements CommandHandler<OrderModel> {

    @Override
    public Object execute(OrderModel commandModel) {

        //订单相关

        return null;
    }
}
