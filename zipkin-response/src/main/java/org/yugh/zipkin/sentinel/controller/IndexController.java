package org.yugh.zipkin.sentinel.controller;

import org.springframework.web.bind.annotation.*;

/**
 * 提供给request
 *
 * @author genhai yu
 */
@RestController
@RequestMapping("/resp")
public class IndexController {


   /* public Mono<String> mono(){

        return Mono.just("simple string ").transform(new SentinelReactorTransformer<>("otherResourceName"));
    }
        */

    @GetMapping(value = "/callResponse")
    public String hello(@RequestParam(value = "name") String name) {
        return "Hello " + name + ", I'm Response!";
    }


}
