package org.yugh.globalauth.annotation;

import java.lang.annotation.*;

/**
 * Skip auth
 *
 * @author yugenhai
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreSkipAuth {

    String message() default "";

}
