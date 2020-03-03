package org.yugh.coral.boot.servlet;

import java.util.Date;

/**
 * For Servlet
 *
 * @author yugenhai
 */
// @Slf4j
// @Aspect
// @Component
// @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
// @ConditionalOnProperty(value = ClientMessageInfo.SERVLET_REQUEST_ENABLE, havingValue = "true")
public class ServletRequestLogAspect {

//    @Pointcut("@within(org.yugh.coral.annotation.WebSessionLog)")
//    public void requestLogAspectAnnotationPointcutForType() {
//        // type
//    }
//
//    @Pointcut("@annotation(org.yugh.coral.annotation.WebSessionLog)")
//    public void requestLogAspectAnnotationPointcutForMethod() {
//        // method
//    }

//    @Pointcut("execution(public * org.yugh.*.*.web.rest.*.*(..))")
//    public void requestLogAspectAnnotationPointcut() {
//        // aop check
//    }

//
//    /**
//     * @param pjp
//     * @return
//     * @throws Throwable
//     */
//    @Around("requestLogAspectAnnotationPointcutForType() || requestLogAspectAnnotationPointcutForMethod()" +
//            "|| requestLogAspectAnnotationPointcut()")
//    public Object invokeRequestLog(ProceedingJoinPoint pjp) throws Throwable {
//        WebSessionLog webSessionLogBO = new WebSessionLog();
//        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
//        WebSessionLog webLog = method.getAnnotation(WebSessionLog.class);
//        if (webLog != null) {
//            String value = webLog.name();
//            webSessionLogBO.setOperation(value);
//            log.info("invokeRequestLog WebSessionLog name's : {}", value);
//        }
//        String className = pjp.getTarget().getClass().getName();
//        String methodName = method.getName();
//        webSessionLogBO.setMethod(className + StringPool.DOT + methodName);
//        log.info("invokeRequestLog WebSessionLog className : {}, methodName : {}", className, methodName);
//        Object[] args = pjp.getArgs();
//        Object[] arguments = new Object[args.length];
//        for (int i = 0; i < args.length; i++) {
//            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse
//                    || args[i] instanceof MultipartFile) {
//                continue;
//            }
//            arguments[i] = args[i];
//        }
//        String params = null;
//        try {
//            params = JSON.toJSONString(arguments);
//        } catch (Exception e) {
//            log.error("invokeRequestLog toJson exception : {}", e.getMessage());
//        }
//        webSessionLogBO.setParams(params);
//        webSessionLogBO.setCreateDate(Instant.now());
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (Objects.isNull(servletRequestAttributes)) {
//            throw new RuntimeException("invokeRequestLog ServletRequestAttributes must not null");
//        }
//        webSessionLogBO.setUserName("yugenhai");
//        webSessionLogBO.setUserNo(159499L);
//        // save params To
//        log.info("invokeRequestLog end params : {}", webSessionLogBO);
//        return pjp.proceed();
//    }

    //    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
    private static class WebSessionLog {

        /**
         * 用户编号
         */
        private Long userNo;

        /**
         * 用户名 yugenhai
         */
        private String userName;

        /**
         * 操作
         */
        private String operation;

        /**
         * 方法名
         */
        private String method;

        /**
         * 参数
         */
        private String params;

        /**
         * 记录IP
         */
        private String ip;

        /**
         * 访问时间
         */
        private Date createDate;
    }
}
