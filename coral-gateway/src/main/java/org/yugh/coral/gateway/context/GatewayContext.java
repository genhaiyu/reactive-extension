package org.yugh.coral.gateway.context;

import lombok.Data;

import java.io.Serializable;

/**
 * Gateway Context
 *
 * @author 余根海
 * @creation 2019-07-10 23:41
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
public class GatewayContext implements Serializable {

    private static final long serialVersionUID = 7420632808330120030L;

    /**
     * next go on
     */
    private boolean doNext = true;


    /**
     * black instance
     */
    private boolean black;

    /**
     * sso token
     * 原SSO Cookie 已作废
     * <p>
     * Modify yugenhai 2019-09-03
     */
    @Deprecated
    private String ssoToken;

    /**
     * 用户生成token ，透传到微服务中，防止跨域问题
     */
    private String userToken;

    /**
     * User no
     */
    @Deprecated
    private String userNo;

    /**
     * path
     */
    @Deprecated
    private String path;

    /**
     * redirectUrl
     */
    private String redirectUrl;


}
