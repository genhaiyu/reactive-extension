package org.yugh.auth.util;

/**
 * //雪花算法生成分布式ID
 *
 * @author: 余根海
 * @creation: 2019-05-26 19:20
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
public class SnowFlakeUtil {


    /**
     * 1：机器码
     * 2：时间偏移
     * 3：硬件码防撞
     *
     * @return
     */
    public static Long nextWaterFlow() {
        return SnowFlakeGenerated.nextId();
    }
}
