package org.yugh.auth.common.enums;

/**
 * //按照部署级别进行管理
 *
 * @author 余根海
 * @creation 2019-06-21 17:08
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
public enum DeployEnum {

    /**
     * 部署级别
     */
    DEV("dev", 1),
    TEST("test", 2),
    PROD("prod", 3);

    private String type;
    private int code ;

    DeployEnum(String type, int code){
        this.type = type;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }}
