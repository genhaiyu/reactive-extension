package org.yugh.gateway.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * Gateway thread pool
 *
 * It’s function is SynUser info
 *
 * see {@link org.yugh.gateway.filter.GatewayDataWorksFilter}
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
        ExecutorService pool = new ThreadPoolExecutor(5, 100, 1000L
                , TimeUnit.MILLISECONDS, new ArrayBlockingQueue(5), namedThreadFactory
                , new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

}
