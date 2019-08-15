package org.yugh.gatewaynew.security;

import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.globalauth.annotation.PreSkipAuth;
import org.yugh.globalauth.common.constants.Constant;
import org.yugh.globalauth.common.enums.ResultEnum;
import org.yugh.globalauth.util.ResultJson;

import java.net.InetAddress;

/**
 * Close this client
 *
 * @author yugenhai
 */
@Slf4j
@PreSkipAuth
@RestController
@RequestMapping("/down")
public class HealthController {

    @Autowired
    private EurekaClient eurekaClient;

    /**
     * Shutdown Gateway Client Switch
     */
    private boolean switchFlag = true;

    private final static String REGEX = "/";

    /**
     * Don't Request the controller
     * <p>
     * My test class
     *
     * @return
     */
    @GetMapping("/offline")
    public ResultJson disable() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            if (Constant.LOCAL_IP.equals(addr.toString().split(REGEX)[1]) || switchFlag) {
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
