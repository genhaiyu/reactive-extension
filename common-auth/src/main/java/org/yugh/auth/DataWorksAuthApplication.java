package org.yugh.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * # data-works-common-auth
 * ## Project Description </br>
 * + 1: Import this project pom
 * + 2: Check Feign Aspect this session
 * + 3: Get data-works User info, Like this "User user = request.getAttribute(Constant.USER_INFO)";
 * + 4: Feign Client, @FeignClient(value = "server1", fallback = Impl.class, configuration = PreFeignInterceptor.class), add configuration = PreFeignAspect.class。
 * + 5: See Notes http://cwiki.xxxxxxxx.com/pages/viewpage.action?pageId=113329378
 *
 * @author yugenhai
 */
@ComponentScan({"org.yugh"})
@SpringBootApplication
public class DataWorksAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataWorksAuthApplication.class, args);
    }
}
