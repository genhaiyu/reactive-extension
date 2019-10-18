package org.yugh.cqrs.goods.command;

/**
 * CQRS Service
 *
 * @author yugenhai
 */
public interface CommandHandler<T> {

    /**
     * 调用此方法进行分发
     *
     * @param commandModel
     * @return
     */
    Object execute(T commandModel);
}
