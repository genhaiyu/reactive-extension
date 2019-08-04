package org.yugh.globalauth.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.globalauth.annotation.CheckSubmitting;
import org.yugh.globalauth.common.constants.Constant;
import org.yugh.globalauth.util.RedisClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 防重提交拦截器
 * <p>
 * 用于方法名
 *
 * @author yugenhai
 */
@Component
public class CheckSubmittingInterceptor implements MethodInterceptor {


    @Autowired
    RedisClient redisClient;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        CheckSubmitting checkSubmitting = methodInvocation.getMethod().getAnnotation(CheckSubmitting.class);
        if (Objects.nonNull(checkSubmitting)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String token = request.getHeader(Constant.TOKEN);

            //FIXME token ?
            //redisClient.
            return null;

        }


        return methodInvocation.proceed();
    }
}
