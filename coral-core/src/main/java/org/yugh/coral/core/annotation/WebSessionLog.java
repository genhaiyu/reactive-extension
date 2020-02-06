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
     * method name
     */
    String name();

    /**
     * into db
     */
    boolean intoDb() default false;
}
