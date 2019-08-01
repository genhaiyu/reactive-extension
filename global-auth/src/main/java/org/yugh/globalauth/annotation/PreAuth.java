package org.yugh.globalauth.annotation;

import java.lang.annotation.*;

/**
 * @author yugenhai
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuth {

    String value() default "";
}