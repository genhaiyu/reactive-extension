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
 * <p>
 * 1：拦截声明注解的类或方法
 * 2：拦截当前声明的package下的类
 *
 * @author yugenhai
 */
@Slf4j
@Aspect
@Component
@Order(Integer.MIN_VALUE + 2)
public class GlobalAuthAspect {


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
        if (Arrays.stream(Constant.ARRAYS).anyMatch(white -> white.equals(method.getName()))) {
            return joinPoint.proceed();
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        log.info("\n ******* Current rpid |=| {}", request.getAttribute(Constant.GLOBAL_RPID));
        //FIXME 这里用redis后，以下代码需要修改

        //FIXME 例如 Object token = redis.get("key");
        //FIXME redis里放入用户登录信息和超时时间，如果没有获取到表示用户已经失效，需要抛出msg


        String token = authService.getToken(request);
        if (StringUtils.isEmpty(token)) {
            log.info("User not login SSO, please Login!");
            throw new RuntimeException("User not login SSO, please Login!");
        }
        Cookie ssoToken = new Cookie(Constant.TOKEN, token);
        request.setAttribute(Constant.TOKEN, token);
        response.addCookie(ssoToken);
        //boolean isLogin = authService.isLogin(request);
        boolean isLogin = true;
        if (!isLogin) {
            log.info("User session expired, please Login! ");
            throw new RuntimeException("User session expired, please Login!");
        }
        //User user = authService.getUserByAuthToken(request);
        User user = new User();
        if (StringUtils.isEmpty(user)) {
            throw new RuntimeException("Get User info exception");
        }
        request.setAttribute(Constant.USER_INFO, user);
        return joinPoint.proceed();
    }

}
