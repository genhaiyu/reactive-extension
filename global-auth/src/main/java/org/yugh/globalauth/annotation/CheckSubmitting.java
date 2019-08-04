package org.yugh.globalauth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防重提交
 *
 * @author yugenhai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSubmitting {

    /**
     * 超时时间 分钟/单位
     *
     * @return
     */
    long timeout() default 1L;

    /**
     * 方法名
     *
     * @return
     */
    String method() default "";

}
