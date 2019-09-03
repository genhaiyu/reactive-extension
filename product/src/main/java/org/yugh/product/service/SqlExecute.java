package org.yugh.product.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yugh.auth.interceptor.PreFeignInterceptor;
import org.yugh.auth.util.ResultJson;
import org.yugh.product.service.impl.SqlServiceImpl;


@FeignClient(value = "BASE-SQL-SUBMISSION-SERVICE", fallbackFactory = SqlServiceImpl.class, configuration = PreFeignInterceptor.class)
public interface SqlExecute {


    @PostMapping(
            value = "/v1/query/execute", produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResultJson sqlExecute(@RequestParam(value = "connectionId") String connectionId, @RequestParam(value = "sqlContent") String sqlContent);
}
