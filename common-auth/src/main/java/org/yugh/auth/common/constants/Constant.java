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
    public final static String SESSION_xx_TOKEN = "xx";

    /**
     * CORS TOKEN
     */
    public final static String CORS_TOKEN = "token";

    /**
     * user info
     */
    public final static String USER_INFO = "userInfo";

    /**
     * global rpid
     */
    public final static String GLOBAL_RPID = "rpid";

    /**
     * instance ip
     */
    public final static String LOCAL_IP = "127.0.0.1";

    /**
     * xx allow headers
     */
    public final static String xx_ALLOW_HEADERS = "X-Requested-with,Cache-Control,Pragma,Content-Type,token,xx,DEVICEID,Authorization-DataWorks-Gateway";

    /**
     * xx allow headers by creek
     */
    public final static String xx_ALLOW_HEADERS_NEW = "X-Requested-with,If-Modified-Since,Last-Modified,Cache-Control,Expires,Content-Type,xx,DEVICEID,Authorization-DataWorks-Gateway,X-Requested-ID";

    /**
     * allow all
     */
    public final static String xx_ALLOW_ALL = "*";

    /**
     * Cookie time out
     */
    public final static int COOKIE_TIME_OUT = 4 * 60 * 60;

    /**
     * request uri
     */
    public final static String PREFIX = "/";


}
