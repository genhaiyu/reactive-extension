package org.yugh.auth.factory;

import lombok.extern.slf4j.Slf4j;
import org.yugh.auth.common.enums.ResultEnum;
import org.yugh.auth.util.ResultJson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Get feign handler
 *
 * @author yugenhai
 */
@Slf4j
public class FeignProxy<T> implements InvocationHandler {

    private Class<T> clazz;

    public FeignProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Get proxy
     *
     * @return
     */
    public T getProxy() {
        return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this));
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.error("\n ******* Feign exception Class name : {}, Method name : {} ", clazz.getSimpleName(), method.getName());
        return ResultJson.failure(ResultEnum.FEIGN_ERROR, method.getName());
    }
}
