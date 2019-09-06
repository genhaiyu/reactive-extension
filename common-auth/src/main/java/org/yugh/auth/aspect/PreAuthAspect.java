package org.yugh.auth.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.auth.annotation.PreSkipAuth;
import org.yugh.auth.common.enums.ResultEnum;
import org.yugh.auth.service.AuthService;
import org.yugh.auth.util.StringPool;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * DataWorks aspect
 * <p>
 *
 * @author yugenhai
 */
@Slf4j
@Aspect
@Component
public class PreAuthAspect {

    @Autowired
    private AuthService authService;


    @Pointcut("@within(org.yugh.auth.annotation.PreAuth)")
    public void pointType() {
    }

    @Pointcut("@annotation(org.yugh.auth.annotation.PreAuth)")
    public void pointMethod() {
    }

    /**
     * luban-dashboard
     */
    @Pointcut("execution(* org.yugh.*.*.*.controller.*.*.*(..))")
    public void pointLubanDashboard() {
    }

    /**
     * luban-backend
     */
    @Pointcut("execution(* org.yugh.*.*.controller.*.*.*.*(..))")
    public void pointLubanBackend() {
    }

    /**
     * habo
     */
    @Pointcut("execution(* org.yugh.*.*.*.controller.*.*(..))")
    public void pointHabo() {
    }

    /**
     * submission
     */
    @Pointcut("execution(* org.yugh.*.*.controller.*.*(..))")
    public void pointLubanSubmission() {
    }

    /**
     * lineage
     */
    @Pointcut("execution(* org.yugh.*.*.controller.*.*(..))")
    public void pointLineage() {
    }

    /**
     * datasource
     */
    @Pointcut("execution(* org.yugh.*.*.controller.*.*(..))")
    public void pointDatasource() {
    }

    /**
     * quota
     */
    @Pointcut("execution(* org.yugh.*.controller.*.*(..))")
    public void pointQuota() {
    }

    /**
     * table
     */
    @Pointcut("execution(* org.yugh.*.*.controller.*.*(..))")
    public void pointTable() {
    }

    @Around("pointLubanDashboard() || pointLubanBackend() || pointHabo() || pointTable()" +
            "|| pointLubanSubmission() || pointLineage() || pointDatasource() || pointQuota()" +
            "|| pointMethod() || pointType()")
    public Object executeAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (method.isAnnotationPresent(PreSkipAuth.class) || joinPoint.getTarget().getClass().isAnnotationPresent(PreSkipAuth.class)) {
            return joinPoint.proceed();
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null == servletRequestAttributes){
            throw new RuntimeException(ResultEnum.BAD_REQUEST.getValue());
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        log.info("\n ******* Current rpid |=| {}", request.getAttribute(StringPool.GLOBAL_RPID));
        String token = authService.getTokenByHeader(request);
        if (StringUtils.isEmpty(token)) {
            log.info("Web --> User not login SSO, please Login!");
            throw new RuntimeException("User not login SSO, please Login To Gateway !");
        }
        boolean isValidate;
        try {
            isValidate = authService.validateToken(token);
        } catch (Exception e) {
            throw new RuntimeException(ResultEnum.TOKEN_ILLEGAL.getValue());
        }
        if (!isValidate) {
            throw new RuntimeException(ResultEnum.TOKEN_EXPIRED.getValue());
        } else {
            return joinPoint.proceed();
        }
    }
}
