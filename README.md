# :sparkles: Core Project 

***
## :palm_tree: Applications Description </br>
+ 1：**eureka** 提供项目注册中心。
+ 2：**customer** 简单的调用关系，被 **product** feign 调用，测试客户端熔断，请见[客户端feign调用熔断代码](https://github.com/yugenhai108/framework-applications/blob/master/product/src/main/java/org/yugh/product/controller/IndexController.java)
+ 3：**zuul** 实现的第一代 netflix开发的zuul路由网关。
+ 4：**gateway** 我在第一代zuul路由网关上实现的gateway，参照了[Spring Cloud Gateway官网](https://cloud.spring.io/spring-cloud-gateway/reference/html/) ，它包含了Spring5.0、Webflux、Reactor响应式等。
+ 5：**common-auth** 开发的项目全局SSO实现，拦截所有项目的请求验证、鉴权，也包括白名单，功能还有feign客户端权限拦截 ——> 到服务端的权限校验。
+ 6：~~**repository** and **domain-event** 主要是领域驱动 `Domain Driven Design`，需要实现在 Entity上加上domain的even事件。（后续再扩展）,目前的框架有 `spring data jpa` and `querydsl jpa` 。~~
+ 7：**domain-event** 代码参照于 [Spring Courses](https://github.com/eugenp/tutorials)。
+ 8：**cqrs** 准确说应该是DDD+cqrs，Command分发器+Domain+Even事件是一个很好的架构设计，目前只是把基本的结构搭建出来，需要后期填充功能场景，我整理了一些架构设计的特点，见`CqrsApplication` [CQRS启动类](https://github.com/yugenhai108/framework-applications/blob/master/cqrs/src/main/java/org/yugh/cqrs/CqrsApplication.java) 。
+ 9：**zipkin-server | zipkin-request | zipkin-response** 一个分布式收集调用关系的看板，**request** 请求 **response**，通过双方注册的zipkin地址上传到**zipkin-server**上，后期扩展es和数据库支持收集存储。

## :evergreen_tree: **gateway** 功能示意图（A schematic）
</br>

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/gateway-auth.png)

</br>

***

## :seedling: "Filter" 的调用顺序(如WebHandler和GlobalFilter实现及个人的Filter实现)和调用全链路的过程
</br>

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/gateway-pic.png)


## :deciduous_tree: Zipkin 链路图（present diagram）
</br>

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/zipkin-detail.png)

</br>

***

## :chestnut: 熔断看板（Hystrix Dashboard） </br> 

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/dashboard.png)

</br>

***
## :ear_of_rice: DDD = Domain Driven Design 架构实现图</br>

![Image text](https://github.com/yugenhai108/framework-applications/blob/master/ddd-project.jpg)

 
