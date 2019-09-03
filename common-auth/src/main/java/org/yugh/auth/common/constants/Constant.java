package org.yugh.auth.common.constants;

/**
 * @author yugenhai
 */
public class Constant {

    /**
     * dataWorks header info
     */
    public final static String DATAWORKS_GATEWAY_HEADERS = "Authorization-DataWorks-Gateway";

    /**
     * cookie
     */
    public final static String SESSION_TOKEN = "token";


    /**
     * user info
     */
    public final static String USER_INFO = "userInfo";

    /**
     * global rpid
     */
    public final static String GLOBAL_RPID = "rpid";

    /**
     * dataworks allow headers
     */
    public final static String DATAWORKS_ALLOW_HEADERS = "X-Requested-with,Cache-Control,Pragma,Content-Type,token,token,DEVICEID,Authorization-DataWorks-Gateway";

    /**
     * datworks allow headers by creek
     */
    @Deprecated
    public final static String DATAWORKS_ALLOW_HEADERS_NEW = "X-Requested-with,If-Modified-Since,Last-Modified,Cache-Control,Expires,Content-Type,token,DEVICEID,Authorization-DataWorks-Gateway,X-Requested-ID";


    /**
     * Cookie time out
     * By luban
     */
    public final static int COOKIE_TIME_OUT = 4 * 60 * 60;


}
