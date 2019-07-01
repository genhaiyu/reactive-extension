package org.yugh.customer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author: YuGenHai
 * @name: AbstractOrderSequenceRandom
 * @creation: 2018/9/21 16:57
 * @notes: svsp + yyyyMMdd + uuid /random
 * @notes: 生成订单号工具类
 */
public abstract class AbstractOrderSequenceRandom {

    /**
     * 订单前缀
     */
    private static final String ORDER_PREFIX = "SVSP";
    /**
     * 时间戳
     */
    private static final String FORMAT = "yyyyMMddHHmmssSSS";
    /**
     * 数字随机
     */
    private static final String NUMBER_CHAR = "0123456789";

    /**
     * 随机数字
     */
    private static final int NUMBER_RANDOM = 5;

    /**
     * @return
     * @author yugenhai
     */
    public synchronized static String createOrderSnRandom(String str) {
        return AbstractOrderSequenceRandomInner.createOrderSnRandomInner(str);
    }

    /**
     * 创建订单号<svsp + yyyy-mm-dd + Random>
     * @Notes 后期用雪花生成
     * @author yugenhai
     */
    private final static class AbstractOrderSequenceRandomInner {
        private synchronized static String createOrderSnRandomInner(String str) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
            StringBuffer sb = new StringBuffer();
            Random random = new Random();
            for (int i = 0; i < NUMBER_RANDOM; i++) {
                sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
            }
            return str + sdf.format(new Date()) + sb.toString();
        }
    }
}
