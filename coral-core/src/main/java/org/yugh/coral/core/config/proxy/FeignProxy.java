package org.yugh.coral.core.config.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author yugenhai
 */
@Slf4j
public class FeignProxy<T> implements InvocationHandler {

    private final static String DEFAULT_METHOD = "toString";
    private Class<T> clazz;

    public FeignProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getProxy() {
        return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (DEFAULT_METHOD.equals(method.getName())) {
            return clazz.getName();
        }
        log.error("\n Feign Exception Class Name : {}, Method name : {} ", clazz.getSimpleName(), method.getName());
        // FIXME 通用返回
        return Objects.toString(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invoke Failed！");
    }
}
