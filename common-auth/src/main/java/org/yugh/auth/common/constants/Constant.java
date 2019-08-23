package org.yugh.auth.common.constants;

/**
 * @author yugenhai
 */
@Deprecated
public class Constant {

    /**
     * dataWorks header info
     */
    public final static String DATAWORKS_GATEWAY_HEADERS = "Authorization-DataWorks-Gateway";

    /**
     * cookie
     */
    public final static String SESSION_xx_TOKEN = "xxSSO";


    /**
     * user info
     */
    public final static String USER_INFO = "userInfo";

    /**
     * global rpid
     */
    public final static String GLOBAL_RPID = "rpid";

    /**
     * guazi allow headers
     */
    public final static String xx_ALLOW_HEADERS = "X-Requested-with,Cache-Control,Pragma,Content-Type,token,xx,DEVICEID,Authorization-DataWorks-Gateway";

    /**
     * guazi allow headers by creek
     */
    @Deprecated
    public final static String xx_ALLOW_HEADERS_NEW = "X-Requested-with,If-Modified-Since,Last-Modified,Cache-Control,Expires,Content-Type,xx,DEVICEID,Authorization-DataWorks-Gateway,X-Requested-ID";


    /**
     * Cookie time out
     * By luban
     */
    public final static int COOKIE_TIME_OUT = 4 * 60 * 60;


}
