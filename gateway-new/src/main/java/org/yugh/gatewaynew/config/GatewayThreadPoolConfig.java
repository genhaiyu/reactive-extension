package org.yugh.gatewaynew.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * //网关线程池
 *
 * @author 余根海
 * @creation 2019-07-12 17:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Configuration
public class GatewayThreadPoolConfig {

    @Bean({"gatewayQueueThreadPool"})
    public ExecutorService buildGatewayQueueThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("DataWorks-Gateway-ThreadPool--%d").build();
        /**
         * 1:核心数
         * 2:最大线程数
         * 3:存活时间
         * 4:数组队列FIFO
         * 5:Spring拒绝策略
         */
        ExecutorService pool = new ThreadPoolExecutor(5, 100, 1000L
                , TimeUnit.MILLISECONDS, new ArrayBlockingQueue(5), namedThreadFactory
                , new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

}
