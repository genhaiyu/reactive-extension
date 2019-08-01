package org.yugh.globalauth.common.constants;

/**
 * @author yugenhai
 */
public class Constant {

    /**
     * 链路头部信息
     */
    public final static String DATAWORKS_GATEWAY_HEADERS = "Authorization-Gateway";

    /**
     * cookie
     */
    public final static String TOKEN = "token";

    /**
     * 用户信息
     */
    public final static String USER_INFO = "userInfo";

    /**
     * 简单全局信息
     */
    public final static String GLOBAL_RPID = "rpid_";

    /**
     * 实例本机
     */
    public final static String LOCAL_IP = "127.0.0.1";


    /**
     * 健康检查云平台
     */
    private final static String READY = "ready";
    private final static String DISABLE = "disable";
    private final static String HEART = "heart";
    private final static String ENABLE = "enable";
    public final static String [] arrays = {READY, DISABLE, HEART, ENABLE};


}
