package org.yugh.gatewaynew.config;

import lombok.Data;

import java.io.Serializable;

/**
 * //网关上下文
 *
 * @author 余根海
 * @creation 2019-07-10 23:41
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
public class GatewayContext implements Serializable {

    private static final long serialVersionUID = 7420632808330120030L;
    /**
     * 是否执行下一步
     */
    private boolean doNext = true;
    /**
     * 黑名单
     */
    private boolean black;
    /**
     * 会话ID
     */
    private String logId;
    /**
     * sso颁发的token
     */
    private String ssoToken;
    /**
     * 用户no
     */
    @Deprecated
    private String userNo;
    /**
     * 请求地址
     */
    @Deprecated
    private String path;
    /**
     * 重定向链接
     */
    private String redirectUrl;
}
