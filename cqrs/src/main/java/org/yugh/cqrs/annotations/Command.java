package org.yugh.cqrs.annotations;

import java.lang.annotation.*;

/**
 * 命令注解器
 *
 * @author yugenhai
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * 加此注解标识异步运行
     * 如果加在CommandHandler 必须返回void，否则会报错
     *
     * @return
     */
    boolean asynchronous() default false;

    /**
     * 重复请求需要加此注解
     * true必须重写 equals 和 hashCode
     *
     * @return
     */
    boolean unique() default false;

    /**
     * unique属性为true
     * 则使用此注解设置超时时间，单位毫秒
     *
     * @return
     */
    long uniqueStorageTimeout() default 0L;
}
