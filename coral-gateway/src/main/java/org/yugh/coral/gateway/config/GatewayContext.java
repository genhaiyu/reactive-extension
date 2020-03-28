/*
 *
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */

package org.yugh.coral.gateway.config;

import java.io.Serializable;

public class GatewayContext implements Serializable {

    private static final long serialVersionUID = 7420632808330120030L;
    private boolean doNext = true;
    private boolean black;
    @Deprecated
    private String ssoToken;
    private String userToken;
    private String path;
    private String redirectUrl;

    public boolean isDoNext() {
        return doNext;
    }

    public void setDoNext(boolean doNext) {
        this.doNext = doNext;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public String getSsoToken() {
        return ssoToken;
    }

    public void setSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return "GatewayContext{" +
                "doNext=" + doNext +
                ", black=" + black +
                ", ssoToken='" + ssoToken + '\'' +
                ", userToken='" + userToken + '\'' +
                ", path='" + path + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                '}';
    }
}
