package org.yugh.eureka.config;

/**
 * //非放行的链接跳转到此处
 * //如果需要跳过这个Point，在初始化拦截里添加需要过滤的请求地址
 *
 * @author: 余根海
 * @creation: 2019-04-09 18:17
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
//@Slf4j
//@Component
public class EurekaAuthenticationEntryPoint  {

   /* implements AuthenticationEntryPoint, Serializable
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(ResultJson.failure(ResultEnum.UNAUTHORIZED, authException.getMessage()).toString());
        printWriter.flush();
        log.info("请求地址 :{}, 信息 : {}", request.getServletPath(),ResultEnum.UNAUTHORIZED.getValue());
    }*/
}
