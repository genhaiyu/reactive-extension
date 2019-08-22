package org.yugh.auth.aspect;

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
import org.yugh.auth.annotation.PreSkipAuth;
import org.yugh.auth.common.constants.Constant;
import org.yugh.auth.pojo.dto.User;
import org.yugh.auth.service.AuthService;
import org.yugh.auth.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * dataWorks aspect
 * <p>
 *
 * @author yugenhai
 */
@Slf4j
@Aspect
@Order(2)
@Component
public class PreAuthAspect {

    @Autowired
    private AuthService authService;

    /**@Autowired private AuthCookieUtils authCookieUtils;*/

    /**@Autowired private RedisClient redisClient;**/

    /**
     * If request.getAttribute(Constant.USER_INFO) == null
     * <p>
     * Must use it @PreAuth
     */
    @Pointcut("@within(org.yugh.auth.annotation.PreAuth)")
    public void pointType() {
    }

    @Pointcut("@annotation(org.yugh.auth.annotation.PreAuth)")
    public void pointMethod() {
    }

    /**
     * luban-dashboard
     */
    @Pointcut("execution(* com.xx.dataworks.*.*.*.controller.*.*.*(..))")
    public void pointLubanDashboard() {
    }

    /**
     * luban-backend
     */
    @Pointcut("execution(* com.xx.dataworks.*.*.controller.*.*.*.*(..))")
    public void pointLubanBackend() {
    }

    /**
     * habo
     */
    @Pointcut("execution(* com.xx.dataworks.*.*.*.controller.*.*(..))")
    public void pointHabo() {
    }

    /**
     * submission
     */
    @Pointcut("execution(* com.xx.dataworks.*.*.controller.*.*(..))")
    public void pointLubanSubmission() {
    }

    /**
     * lineage
     */
    @Pointcut("execution(* com.xx.dataworks.*.*.controller.*.*(..))")
    public void pointLineage() {
    }

    /**
     * datasource
     */
    @Pointcut("execution(* com.xx.dataworks.*.*.controller.*.*(..))")
    public void pointDatasource() {
    }

    /**
     * quota
     */
    @Pointcut("execution(* com.xx.dataworks.*.controller.*.*(..))")
    public void pointQuota() {
    }

    /**
     * table
     */
    @Pointcut("execution(* com.xx.dataworks.*.*.controller.*.*(..))")
    public void pointTable() {
    }

    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointLubanDashboard() || pointLubanBackend() || pointHabo() || pointTable()" +
            "|| pointLubanSubmission() || pointLineage() || pointDatasource() || pointQuota()" +
            "|| pointMethod() || pointType()")
    public Object executeDataWorksAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (method.isAnnotationPresent(PreSkipAuth.class) || joinPoint.getTarget().getClass().isAnnotationPresent(PreSkipAuth.class)) {
            return joinPoint.proceed();
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        log.info("\n ******* Current rpid |=| {}", request.getAttribute(Constant.GLOBAL_RPID));
        String token = authService.getToken(request);
        //msg throw to
        if (StringUtils.isEmpty(token)) {
            log.info("User not login SSO, please Login!");
            throw new RuntimeException("User not login SSO, please Login!");
        }
        boolean isLogin = authService.isLogin(request);
        if (!isLogin) {
            authService.removeCookieByAspect(response);
            WebUtils.removeSession(request, Constant.USER_INFO);
            log.info("User session expired, please Login! ");
            throw new RuntimeException("User session expired, please Login!");
        }
        User user = authService.getUserByAuthToken(request);
        if (StringUtils.isEmpty(user)) {
            throw new RuntimeException("Get User info exception");
        }
        WebUtils.setSession(request, Constant.USER_INFO, user);
        return joinPoint.proceed();
    }

}
