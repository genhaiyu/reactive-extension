package org.yugh.auth.annotation;

import java.lang.annotation.*;

/**
 * Pre auth
 *
 * @author yugenhai
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuth {

    /**
     * 设置一个值
     *
     * @return
     */
    String value() default "";
}