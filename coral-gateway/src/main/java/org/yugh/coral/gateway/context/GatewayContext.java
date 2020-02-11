package org.yugh.coral.gateway.context;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 余根海
 * @creation 2019-07-10 23:41
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
public class GatewayContext implements Serializable {

    private static final long serialVersionUID = 7420632808330120030L;

    private boolean doNext = true;

    private boolean black;

    @Deprecated
    private String ssoToken;

    /**
     * 用户生成token ，透传到微服务中，防止跨域问题
     */
    private String userToken;

    @Deprecated
    private String userNo;
    @Deprecated
    private String path;

    private String redirectUrl;


}
