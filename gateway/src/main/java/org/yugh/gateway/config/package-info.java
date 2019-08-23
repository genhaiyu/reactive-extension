/**
 * @author yugenhai
 */
package org.yugh.gateway.config;
/*
 * Gateway thread pool
 * <p>
 * It’s function is SynUser info
 * <p>
 * see {@link GatewayDataWorksFilter}
 *
 * @author 余根海
 * @creation 2019-07-12 17:52
 * @Copyright © 2019 yugenhai. All rights reserved.
    @Configuration
    public class GatewayThreadPoolConfig {
    @Bean({"gatewayQueueThreadPool"})
    public ExecutorService buildGatewayQueueThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("DataWorks-Gateway-ThreadPool--%d").build();
        ExecutorService pool = new ThreadPoolExecutor(3, 100, 1000L
                , TimeUnit.MILLISECONDS, new ArrayBlockingQueue(3), namedThreadFactory
                , new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }
}
*/