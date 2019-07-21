package org.yugh.common.util.jwt;

/**
 */
public interface IJWTInfo {

    /**
     * 获取用户ID  no
     * @return
     */
    String getId();

    /**
     * 获取用户名 邮箱前缀
     * @return
     */
    String getUniqueName();


    /**
     * 获取中文名称
     * @return
     */
    String getRealName();

}
