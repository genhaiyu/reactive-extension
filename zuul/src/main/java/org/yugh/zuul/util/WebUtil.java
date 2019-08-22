package org.yugh.zuul.util;

import javax.servlet.http.HttpServletRequest;

/**
 * //context path
 *
 * @author: 余根海
 * @creation: 2019-06-26 17:43
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
public abstract class WebUtil {


    /**
     * 和白名单匹配
     * @author: 余根海
     * @creation: 2019-06-26 17:43
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        String realUri = uri.replace(contextPath, "");
        while (realUri.startsWith("/")) {
            realUri = realUri.substring(1);
        }
        return realUri.replaceAll("/+", "/");
    }


    /**
     * 去除项目后缀线
     * @author: 余根海
     * @creation: 2019-06-26 17:45
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        if (("/").equals(contextPath)) {
            return "";
        }
        return contextPath;
    }


}
