package org.yugh.coral.boot.reactive.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

/**
 * @author yugenhai
 */
@Slf4j
@Aspect
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class CustomRequestContextAspect {


    @Pointcut("execution(public * org.yugh.coral.*.*.web.rest.*.*(..))")
    public void requestLogAspectAnnotationPointcut() {
        // aop check
    }
}
