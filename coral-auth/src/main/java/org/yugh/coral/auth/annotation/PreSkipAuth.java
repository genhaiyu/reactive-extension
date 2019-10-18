package org.yugh.coral.auth.annotation;

import java.lang.annotation.*;

/**
 * Skip auth
 *
 * @author yugenhai
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreSkipAuth {

    /**
     * 说一句话
     *
     * @return
     */
    String message() default "";

}
