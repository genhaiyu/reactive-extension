package org.yugh.globalauth.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.globalauth.common.constants.Constant;
import org.yugh.globalauth.pojo.dto.User;
import org.yugh.globalauth.service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * aspect
 *
 * @author yugenhai
 */
@Slf4j
@Aspect
@Component
@Order(Integer.MIN_VALUE)
public class PreAuthAspect {


    @Autowired
    private AuthService authService;

    @Pointcut("@within(org.yugh.globalauth.annotation.PreAuth)")
    public void pointType() {
        //type
    }

    @Pointcut("@annotation(org.yugh.globalauth.annotation.PreAuth)")
    public void pointMethod() {
        //Method
    }

    @Pointcut("execution(* org.yugh.*.controller.*.*(..))")
    public void pointService() {
        //Service
    }

    @Pointcut("execution(* org.yugh.*.*.controller.*.*(..))")
    public void pointOther() {
        //Service
    }


    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointType() || pointMethod() || pointService() || pointOther()")
    public Object executeGateway(ProceedingJoinPoint joinPoint) throws Throwable {
        /*ReactiveRequestContextHolder.getRequest();*/
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (Arrays.stream(Constant.arrays).anyMatch(white -> white.equals(method.getName()))) {
            return joinPoint.proceed();
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        String token = authService.getToken(request);
        //msg已经抛出，各个微服务需要把最大的Exception的msg设置为动态变量
        if (StringUtils.isEmpty(token)) {
            log.info("用户未登录，非法访问");
            throw new RuntimeException("用户未登录，非法访问");
        }
        Cookie ssoToken = new Cookie(Constant.TOKEN, token);
        request.setAttribute(Constant.TOKEN, token);
        response.addCookie(ssoToken);
        //boolean isLogin = authService.isLogin(request);
        boolean isLogin = true;
        if (!isLogin) {
            log.info("用户会话已失效, 请重新登录");
            throw new RuntimeException("用户会话已失效, 请重新登录");
        }
        //User user = authService.getUserByAuthToken(request);
        User user = new User();
        if (StringUtils.isEmpty(user)) {
            throw new RuntimeException("用户获取异常");
        }
        request.setAttribute(Constant.USER_INFO, user);
        return joinPoint.proceed();
    }

}
