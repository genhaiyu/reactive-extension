package org.yugh.coral.auth.factory;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Feign fallback factory
 *
 * Init Load Feign Interface
 *
 * @author yugenhai
 */
@Slf4j
public class DefaultFallbackFactory<T> implements FallbackFactory<T>, InitializingBean {

    private Class<T> clazz;
    private T t;

    @Override
    public T create(Throwable throwable) {
        log.error("\n ******* Feign Service : {}, Call Back Fall Msg : {} ", clazz.getSimpleName(), throwable.getMessage());
        return t;
    }

    @Override
    public void afterPropertiesSet() {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType pt = ParameterizedType.class.cast(type);
        clazz = Class.class.cast(pt.getActualTypeArguments()[0]);
        t = new FeignProxy<>(clazz).getProxy();
    }
}

