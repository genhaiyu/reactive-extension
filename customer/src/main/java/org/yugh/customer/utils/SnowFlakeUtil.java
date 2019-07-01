package org.yugh.customer.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * //雪花算法生成分布式ID
 *
 * @author: 余根海
 * @creation: 2019-05-26 19:20
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
public class SnowFlakeUtil {


    static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static Long nextWaterFlow() {
        return snowflake.nextId();
    }

}
