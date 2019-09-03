package org.yugh.auth.annotation;

import java.lang.annotation.*;

/**
 * Pre auth
 *
 * @author yugenhai
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuth {

    String value() default "";
}