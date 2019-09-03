/**
 * @author yugenhai
 * @Copyright © 2019 yugenhai. All rights reserved.
 * private final Object shutdownMonitor = new Object();
 */
package org.yugh.gateway.security;

import com.netflix.discovery.EurekaClient;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.auth.annotation.PreSkipAuth;
import org.yugh.auth.common.enums.ResultEnum;
import org.yugh.auth.config.AuthConfig;
import org.yugh.auth.util.ResultJson;
import org.yugh.auth.util.StringPool;

import java.net.InetAddress;

/**
 * Close this client
 *
 * @author yugenhai
 */
@Slf4j
@PreSkipAuth
@RestController
@RequestMapping("switch")
public class OfflineController {

    @Autowired
    private EurekaClient eurekaClient;
    @Autowired
    private AuthConfig authConfig;


    /**
     * @return
     */
    @Synchronized
    @GetMapping("offline")
    public ResultJson offline() {
        try {
            String flag = authConfig.getShutdownClient();
            Asserts.notEmpty(flag, "AuthConfig's ShutdownClient '" + flag + "' is empty");
            Boolean shutdown = Boolean.valueOf(flag);
            InetAddress addr = InetAddress.getLocalHost();
            if (StringPool.INSTANCE_IP.equals(addr.toString().split(StringPool.SLASH)[1]) || shutdown) {
                eurekaClient.shutdown();
                log.info("Shutdown Gateway Client is success !!!");
                return ResultJson.ok(null);
            }
        } catch (Exception e) {
            log.error("Shutdown Gateway Client Exception : {}", e);
        }
        return ResultJson.failure(ResultEnum.GATEWAY_SERVER_ERROR);
    }
}
