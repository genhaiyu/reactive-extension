package org.yugh.coral.core.annotation;

import java.lang.annotation.*;

/**
 * @author yugenhai
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebSessionLog {

    /**
     * 接口名
     *
     * @return
     */
    String name();

    /**
     * 是否持久
     *
     * @return
     */
    boolean intoDb() default false;
}
