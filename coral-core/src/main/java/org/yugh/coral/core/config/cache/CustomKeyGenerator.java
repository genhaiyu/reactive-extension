package org.yugh.coral.core.config.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author yugenhai
 */
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    @SuppressWarnings("all")
    public Object generate(Object object, Method method, Object... params) {
        return String.format("%s_%s_%s",
                object.getClass().getSimpleName(),
                method.getName(),
                StringUtils.arrayToDelimitedString(params, "_"));
    }
}
