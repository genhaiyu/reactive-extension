package org.yugh.zuul.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * //
 *
 * @author: 余根海
 * @creation: 2019-07-01 16:27
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@RestController
//@Api(description = "index控制器")
public class IndexController {


    @GetMapping("index1")
    public Object index1(HttpServletRequest request) {

        log.info("========> url:{}", request.getServletPath());

        return request.getServletPath();
    }

}
