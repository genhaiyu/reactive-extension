# :sparkles: Core Project 

***
##  Description </br>
+ 1：`eureka` 提供项目注册中心。
+ 2：`customer` 简单的调用关系，被 `product` feign 调用，测试客户端熔断，请见 `IndexController` method [info](https://github.com/yugenhai108/framework-applications/blob/master/product/src/main/java/org/yugh/product/controller/IndexController.java)  HystrixCommand 
+ 3：`gateway` 我实现的第一代 netflix开发的zuul网关，当下也是流行的组件。
+ 4：`gateway-new` 我在第一代网关上实现的gateway，参照了 Spring Cloud Gateway官网 [info](https://cloud.spring.io/spring-cloud-gateway/reference/html/) ，它包含了Spring5.0、Webflux、Reactor响应式等。
+ 5：`global-auth` 开发的项目全局SSO实现，拦截所有项目的请求验证、鉴权，也包括白名单，功能还有feign客户端权限拦截 ——> 到服务端的权限校验。
+ 6：`repository` and `domain-event` 主要是领域驱动 `Domain Driven Design`，需要实现在 Entity上加上domain的even事件。（后续再扩展）,目前的框架有 `spring data jpa` and `querydsl jpa` 。`domain-event` 代码参照于 [Spring Courses](https://github.com/eugenp/tutorials)。
+ 7：`zipkin-server | zipkin-request | zipkin-response` 一个分布式收集调用关系的看板，`request` 请求 `response`，通过双方注册的zipkin地址上传到zipkin-server上，后期扩展es和数据库支持收集存储。

## 功能示意图（A schematic）:
</br>

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/gateway-auth.png)

</br>

***

## 链路图(present diagram):
</br>

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/zipkin-detail.png)

</br>

***

## Hystrix Dashboard </br> 

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/dashboard.png)

</br>

***
## DDD = Domain Driven Design 架构实现图</br>

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/ddd-project.jpg)

 
