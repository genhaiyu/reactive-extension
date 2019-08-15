package org.yugh.globalauth.common.constants;

import java.net.URLEncoder;

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
    public final static String SESSION_XX_TOKEN = "XXSSO";

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
     * XX allow headers
     */
    public final static String XX_ALLOW_HEADERS = "x-requested-with,Cache-Control,Pragma,Content-Type,token,clientType,XXSSO,DEVICEID";

    /**
     * XX allow headers by creek
     */
    public final static String XX_ALLOW_HEADERS_NEW = "Origin,No-Cache,X-Requested-with,If-Modified-Since,Last-Modified,Cache-Control,Expires,Content-Type,XXSSO,DEVICEID,Authorization-DataWorks-Gateway,X-Requested-ID";

    /**
     * allow all
     */
    public final static String XX_ALLOW_ALL = "*";

    /**
     * Cookie time out
     */
    public final static int COOKIE_TIME_OUT = 2 * 24 * 60 * 60;

    /**
     * request uri
     */
    public final static String PREFIX = "/";

    /**
     * Env test
     */
    public final static String ENV_TEST = "test";

    /**
     * Env pro
     */
    public final static String ENV_PRO = "pro";



    public static void main(String[] args) throws Exception {
        String s = "test.xx.com:6300/api/luban-dashboard/v1/chart/list?id=1&name=2&createdAtStart=1&createdAtEnd=2&pageIndex=1&perPage=10";
        String str = URLEncoder.encode(s, "UTF-8");
        System.out.println(str);
    }

}
