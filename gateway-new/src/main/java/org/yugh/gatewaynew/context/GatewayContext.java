package org.yugh.gatewaynew.context;

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
     * next go on
     */
    private boolean doNext = true;


    /**
     * black instance
     */
    private boolean black;

    /**
     * sso token
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
     * redirectUrl
     */
    private String redirectUrl;


}
