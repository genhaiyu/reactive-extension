package org.yugh.common.common.annotation;

import java.lang.annotation.*;

/**
 * 当前用户
 * @author yugenhai
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserInfo {

}
