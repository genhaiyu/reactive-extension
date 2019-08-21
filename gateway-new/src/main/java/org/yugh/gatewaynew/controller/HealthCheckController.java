package org.yugh.gatewaynew.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.auth.annotation.PreSkipAuth;

@PreSkipAuth
@RestController
@Deprecated
@RequestMapping("/health")
public class HealthCheckController {

    private static volatile boolean serverStatus = true;

    /**
     * 状态检查，用于发布系统检查服务是否启动成功
     *
     * @return
     */
    @RequestMapping(value = "/healthCheck", method = {RequestMethod.GET})
    public String heart(ServerHttpResponse response) {
        try {
            if (serverStatus) {
                return "ok";
            } else {
                response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                return "failed";
            }
        } catch (Exception e) {
            return "failed";
        }
    }

    /**
     * 就绪检查，用于发布系统检查服务是否已经就绪，可以接受流量
     *
     * @return
     */
    @RequestMapping(value = "/readinessCheck", method = {RequestMethod.GET})
    public String ready(ServerHttpResponse response) {
        try {
            if (serverStatus) {
                return "ok";
            } else {
                response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                return "failed";
            }
        } catch (Exception e) {
            return "failed";
        }
    }

    /**
     * 禁用服务，使得nginx 流量不打入这个服务
     *
     * @return
     */
    @RequestMapping(value = "disable", method = {RequestMethod.GET})
    public String disable() {
        try {
            serverStatus = false;
            return "ok";
        } catch (Exception e) {
            return "failed";
        }
    }

    /**
     * 启用服务，使得nginx 流量打入这个服务
     *
     * @return
     */
    @RequestMapping(value = "enable", method = {RequestMethod.GET})
    public String enable() {
        try {
            serverStatus = true;
            return "ok";
        } catch (Exception e) {
            return "failed";
        }
    }


}
