package org.yugh.zipkin.sentinel.controller;

import org.yugh.zipkin.sentinel.feign.ICallResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author genhai yu
 */
@RestController
public class CallResponseController {

    @Autowired
    private ICallResponseService callResponseService;


    @GetMapping(value = "call")
    public String callResponse(String name) {
        String response = callResponseService.callResponse(name);
        return response;
    }

}
