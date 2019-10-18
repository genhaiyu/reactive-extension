package org.yugh.coral.auth.annotation;

import java.lang.annotation.*;

/**
 * 操作记录
 *
 * @author yugenhai
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Operation {

    /**
     * 设置一个值
     *
     * @return
     */
    String value() default "";
}
