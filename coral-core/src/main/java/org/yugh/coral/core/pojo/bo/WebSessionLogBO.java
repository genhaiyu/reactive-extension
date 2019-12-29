package org.yugh.coral.core.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author yugenhai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSessionLogBO implements Serializable {

    private static final long serialVersionUID = 3425743634999687631L;


    /**
     * 用户编号
     */
    private Long userNo;

    /**
     * 用户名 yugenhai
     */
    private String userName;

    /**
     * 操作
     */
    private String operation;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * 记录IP
     */
    private String ip;

    /**
     * 访问时间
     */
    private Instant createDate;
}
