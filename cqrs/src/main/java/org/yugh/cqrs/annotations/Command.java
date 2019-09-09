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
     * Suggestion for a Server that this command should checked if the same command is sent again.<br>
     * If true than command class must implement equals and hashCode
     *
     * @return
     */
    boolean unique() default false;

    /**
     * If unique is true than this property may specify maximum timeout in miliseconds before same command can be executed
     *
     * @return
     */
    long uniqueStorageTimeout() default 0L;
}
