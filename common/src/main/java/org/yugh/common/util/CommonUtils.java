package org.yugh.common.util;

import org.springframework.beans.BeanUtils;

/**
 * //工具类
 *
 * @author 余根海
 * @creation 2019-07-10 17:36
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
public class CommonUtils {


    /**
     * 简单复制
     *
     * @param obj1
     * @param obj2
     */
    public static void copyProperties(final Object obj1, final Object obj2) {
        BeanUtils.copyProperties(obj1, obj2);
    }



}
