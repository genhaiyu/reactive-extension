package org.yugh.zipkin.sentinel.feign.impl;

import org.yugh.zipkin.sentinel.feign.ICallResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author genhai yu
 */
@Component
public class CallResponseServiceImpl implements ICallResponseService {

    @Override
    public String callResponse(String name) {
        System.out.println("\n ================> feign调用失败");
        return String.format(HttpStatus.FORBIDDEN.getReasonPhrase(), "feign调用失败");
    }
}
