/*
 * Copyright (c) 2019-2029, yugenhai108@gmail.com.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yugh.coral.boot.servlet;

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
import org.yugh.coral.core.pojo.bo.WebSessionLogBO;
import org.yugh.coral.core.utils.JsonUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Objects;

/**
 * for servlet
 *
 * @author yugenhai
 */
@Slf4j
@Aspect
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(value = StringPool.REQ_LOG_PROPS_PREFIX + ".enabled", havingValue = "true", matchIfMissing = true)
public class ServletRequestLogAspect {

    @Pointcut("@within(org.yugh.coral.core.annotation.WebSessionLog)")
    public void requestLogAspectAnnotationPointcutForType() {
        // type
    }

    @Pointcut("@annotation(org.yugh.coral.core.annotation.WebSessionLog)")
    public void requestLogAspectAnnotationPointcutForMethod() {
        // method
    }

    @Pointcut("execution(public * org.yugh.coral.*.*.web.rest.*.*(..))")
    public void requestLogAspectAnnotationPointcut() {
        // aop check
    }


    /**
     * If you used starter-web, auto loadClass
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("requestLogAspectAnnotationPointcutForType() || requestLogAspectAnnotationPointcutForMethod()" +
            "|| requestLogAspectAnnotationPointcut()")
    public Object invokeRequestLogWithXesAppCore(ProceedingJoinPoint pjp) throws Throwable {
        WebSessionLogBO webSessionLogBO = new WebSessionLogBO();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        WebSessionLog webLog = method.getAnnotation(WebSessionLog.class);
        if (webLog != null) {
            String value = webLog.name();
            webSessionLogBO.setOperation(value);
            log.info("invokeRequestLogWithXesAppCore WebSessionLog name's : {}", value);
        }
        String className = pjp.getTarget().getClass().getName();
        String methodName = method.getName();
        webSessionLogBO.setMethod(className + StringPool.DOT + methodName);
        log.info("invokeRequestLogWithXesAppCore WebSessionLog className : {}, methodName : {}", className, methodName);
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
            log.error("invokeRequestLogWithXesAppCore toJson exception : {}", e.getMessage());
        }
        webSessionLogBO.setParams(params);
        webSessionLogBO.setCreateDate(Instant.now());
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            throw new RuntimeException("invokeRequestLogWithXesAppCore ServletRequestAttributes must not null");
        }
        webSessionLogBO.setUserName("yugenhai");
        webSessionLogBO.setUserNo(159499L);
        // save params To
        log.info("invokeRequestLogWithXesAppCore end params : {}", webSessionLogBO);
        return pjp.proceed();
    }
}
