package org.yugh.gatewaynew.annotation;

import java.lang.annotation.*;

/**
 * @author yugh
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuth {

    String value();
}
