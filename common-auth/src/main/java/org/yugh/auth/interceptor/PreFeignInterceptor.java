package org.yugh.auth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.auth.common.constants.Constant;
import org.yugh.auth.service.AuthService;

import javax.servlet.http.HttpServletRequest;

/**
 * dataWorks feign aspect
 *
 * @author yugenhai
 */
@Component
public class PreFeignInterceptor implements RequestInterceptor {

    private final AuthService authService;

    @Autowired
    public PreFeignInterceptor(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 这里有两种实现方式：
     * <p>
     * 1： 此auth 组件每个微服务引用时每次去做当前失效或权限校验
     * 保证客户端和服务端随时监听异常情况，详情见gateway-auth.png 的service A service B的架构示意
     * <p>
     * <p>
     * 2：从网关Gateway组件进来后验证用户和权限后，将用户进行再次加密如 jwt，加token以后通过接口调用把token进行透传
     * 每个微服务进行解token。
     *
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = authService.getToken(request);
        requestTemplate.header(Constant.SESSION_TOKEN, token);
    }
}
