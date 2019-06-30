package org.yugh.eureka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.yugh.eureka.common.ResultJson;
import org.yugh.eureka.enums.ResultEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * //无权限
 *
 * @author: 余根海
 * @creation: 2019-06-30 19:14
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@Component("eurekaAuthenticationAccessDeniedHandler")
public class EurekaAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(ResultJson.failure(ResultEnum.FORBIDDEN, e.getMessage()).toString());
        printWriter.flush();
        log.info("请求地址 :{}, 信息 : {}", request.getServletPath(),ResultEnum.FORBIDDEN.getValue());

    }
}
