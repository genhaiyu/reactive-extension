package org.yugh.common.util.jwt;

import java.io.Serializable;


public class JWTInfo implements Serializable,IJWTInfo {
    private String no;
    private String userName;
    private String realName;

    public JWTInfo(String no, String userName, String realName) {
        this.no = no;
        this.userName = userName;
        this.realName = realName;
    }

    @Override
    public String getUniqueName() {
        return userName;
    }

    @Override
    public String getRealName() {
        return realName;
    }

    @Override
    public String getId() {
        return no;
    }


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JWTInfo jwtInfo = (JWTInfo) o;

        return userName != null ? userName.equals(jwtInfo.userName) : jwtInfo.userName == null;
    }

    @Override
    public int hashCode() {
        return userName != null ? userName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "JWTInfo{" +
                "no='" + no + '\'' +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
