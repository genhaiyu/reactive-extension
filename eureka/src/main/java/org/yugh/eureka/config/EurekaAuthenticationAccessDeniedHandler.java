package org.yugh.eureka.config;

/**
 * //无权限
 * 1:Authentication 在eureka里不用验证用户的信息
 *
 * @author: 余根海
 * @creation: 2019-06-30 19:14
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
//@Slf4j
//@Component("eurekaAuthenticationAccessDeniedHandler")
public class EurekaAuthenticationAccessDeniedHandler  {
 /*implements AccessDeniedHandler
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(ResultJson.failure(ResultEnum.FORBIDDEN, e.getMessage()).toString());
        printWriter.flush();
        log.info("请求地址 :{}, 信息 : {}", request.getServletPath(),ResultEnum.FORBIDDEN.getValue());

    }*/
}
