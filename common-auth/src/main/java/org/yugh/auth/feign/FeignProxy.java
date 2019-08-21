package org.yugh.auth.feign;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.yugh.auth.util.JsonResult;

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
        log.error("\n ******* Feign exception Class Method : {}, Method name : {} ", clazz.getSimpleName(), method.getName());
        return JsonResult.buildErrorResult(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Feign调用失败！");
    }

}
