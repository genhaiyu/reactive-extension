package org.yugh.coral.core.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.yugh.coral.core.annotation.WebSessionLog;
import org.yugh.coral.core.common.constant.StringPool;
import org.yugh.coral.core.common.launcher.LogLevel;
import org.yugh.coral.core.pojo.bo.WebSessionLogBO;
import org.yugh.coral.core.utils.JsonUtils;
import org.yugh.coral.core.utils.ObjectUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.time.Instant;

/**
 * @author yugenhai
 */
@Slf4j
@Aspect
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(value = LogLevel.REQ_LOG_PROPS_PREFIX + ".enabled", havingValue = "true", matchIfMissing = true)
public class RequestLogAspect {


    @Pointcut("@within(org.yugh.coral.core.annotation.WebSessionLog)")
    public void requestLogAspectAnnotationPointcutForType() {
        // type
    }

    @Pointcut("@annotation(org.yugh.coral.core.annotation.WebSessionLog)")
    public void requestLogAspectAnnotationPointcutForMethod() {
        // method
    }

    @Pointcut("execution(* org.yugh.coral.*.controller.*.*(..))")
    public void requestLogAspectAnnotationPointcut() {
        // aop check
    }


    /**
     * // FIXME   invalid aspect
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("requestLogAspectAnnotationPointcutForType() || requestLogAspectAnnotationPointcutForMethod()" +
            "|| requestLogAspectAnnotationPointcut()")
    public Object invokeRequestLogWithAppCore(ProceedingJoinPoint pjp) throws Throwable {
        WebSessionLogBO webSessionLogBO = new WebSessionLogBO();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        WebSessionLog webLog = method.getAnnotation(WebSessionLog.class);
        if (webLog != null) {
            String value = webLog.name();
            webSessionLogBO.setOperation(value);
            log.info("invokeRequestLogWithAppCore WebSessionLog name's : {}", value);
        }
        String className = pjp.getTarget().getClass().getName();
        String methodName = method.getName();
        webSessionLogBO.setMethod(className + StringPool.DOT + methodName);
        log.info("invokeRequestLogWithAppCore WebSessionLog className : {}, methodName : {}", className, methodName);
        Object[] args = pjp.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        String params = null;
        try {
            params = JsonUtils.paramToJson(arguments);
        } catch (Exception e) {
            log.error("invokeRequestLogWithAppCore toJson exception : {}", e.getMessage());
        }
        webSessionLogBO.setParams(params);
        webSessionLogBO.setCreateDate(Instant.now());
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isEmpty(servletRequestAttributes)) {
            throw new RuntimeException("temp msg");
        }
        webSessionLogBO.setUserName("yugenhai");
        webSessionLogBO.setUserNo(159499L);
        // save params To
        log.error("invokeRequestLogWithAppCore end params : {}", webSessionLogBO);
        return pjp.proceed();
    }
}
