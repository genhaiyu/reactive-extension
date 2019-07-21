# :sparkles: Spring cloud Framework Demo

***
##  Demo Description </br>
+ 1：启动 eureka
+ 2：gateway or gateway-new(微服务配置到网关，所有请求走网关) 
+ 3：熔断测试 product -> feign -> customer
+ 4：springboot admin功能过于简单，仅仅一个demo
+ 5：zipkin-server单独搜集链路服务，zipkin-response,zipkin-request配置
```java
zipkin:
    base-url: http://localhost:9411/
```
</br>
***

## 链路图:
</br>

![Image text](https://github.com/yugenhai108/spring-cloud/blob/master/zipkin-detail.png)

</br>

+ 6：repository服务(包含jpa、DDD基础规范)
</br>
***
## Hystrix Dashboard </br> 

![Image text](https://github.com/yugenhai108/spring-cloud/blob/master/dashboard.png)

