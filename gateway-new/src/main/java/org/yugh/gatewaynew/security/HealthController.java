package org.yugh.gatewaynew.security;

import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author yugenhai
 */
@Slf4j
@Component
@RequestMapping("/security")
@ConditionalOnBean(EurekaClient.class)
public class HealthController {

    /*@Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/offline")
    public ResultJson disable() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String remoteHost = request.getRemoteHost();
        log.info("=========>  下线的请求 : {} ", remoteHost);
        if (!Constant.LOCAL_IP.equals(remoteHost)) {
            log.warn("下线的必须是 '" + Constant.LOCAL_IP + "' , 当前实例本身 {}   ", remoteHost);
            return ResultJson.failure(ResultEnum.BAD_REQUEST, "下线的必须是 '" + Constant.LOCAL_IP + "' ,  当前的 (" + remoteHost + " ) ");
        }
        log.info("=========> dataWorks Gateway服务下线 !!!");
        eurekaClient.shutdown();
        return ResultJson.ok(null);
    }*/
}
