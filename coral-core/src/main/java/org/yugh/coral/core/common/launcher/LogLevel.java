package org.yugh.coral.core.common.launcher;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求日志级别，来源 okHttp
 *
 * @author yugenhai
 */
@Getter
@AllArgsConstructor
public enum LogLevel {
    /**
     * No logs.
     */
    NONE(0),

    /**
     * Logs request and response lines.
     *
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1 (3-byte body)
     *
     * <-- 200 OK (22ms, 6-byte body)
     * }</pre>
     */
    BASIC(1),

    /**
     * Logs request and response lines and their respective headers.
     *
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     * --> END POST
     *
     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     * <-- END HTTP
     * }</pre>
     */
    HEADERS(2),

    /**
     * Logs request and response lines and their respective headers and bodies (if present).
     *
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     *
     * Hi?
     * --> END POST
     *
     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     *
     * Hello!
     * <-- END HTTP
     * }</pre>
     */
    BODY(3);

    /**
     * 请求日志配置前缀
     */
    public static final String REQ_LOG_PROPS_PREFIX = "xx.log.request";
    /**
     * 控制台日志是否启用
     */
    public static final String CONSOLE_LOG_ENABLED_PROP = "xx.log.console.enabled";

    /**
     * 级别
     */
    private int level;

    /**
     * 当前版本 小于和等于 比较的版本
     *
     * @param level LogLevel
     * @return 是否小于和等于
     */
    public boolean lte(LogLevel level) {
        return this.level <= level.level;
    }

}
