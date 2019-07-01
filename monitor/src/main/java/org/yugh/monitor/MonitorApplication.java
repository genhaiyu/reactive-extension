package org.yugh.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * //监控看板
 *
 * @author: 余根海
 * @creation: 2019-04-09 18:17
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@EnableTurbine
@SpringCloudApplication
@EnableHystrixDashboard
//@EnableZipkinServer
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }

}
