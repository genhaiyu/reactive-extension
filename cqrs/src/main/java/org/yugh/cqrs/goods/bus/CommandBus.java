package org.yugh.cqrs.goods.bus;

import org.springframework.stereotype.Component;
import org.yugh.cqrs.goods.command.CommandHandler;

/**
 * 命令 总线
 *
 * @author yugenhai
 */
@Component
public class CommandBus {

    /**
     * 分发器
     *
     * @param cmd
     * @param model
     * @param <T>
     * @return
     */
    public <T> Object dispatch(CommandHandler<T> cmd, T model) {
        return cmd.execute(model);
    }
}
