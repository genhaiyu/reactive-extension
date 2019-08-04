package org.yugh.globalauth.annotation;

import java.lang.annotation.*;

/**
 * 是否验证当前类或者方法
 *
 * @author yugenhai
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuth {

    String value() default "";
}