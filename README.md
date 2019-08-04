# :sparkles: Distribute Core Project 

***
##  Description </br>
+ 1：`eureka` It's springboot Registered，It is not necessary，default prot 8700。 ^~^
+ 2：`customer` simple test from `product` feign invoke，test netflix.hystrix，see class `IndexController` method [info](https://github.com/yugenhai108/spring-cloud/blob/master/product/src/main/java/org/yugh/product/controller/IndexController.java)  HystrixCommand ^~^
+ 3：`gateway` Is netflix.zuul of ZuulFilter implements，It's also popular Component。^~^
+ 4：`gateway-new` I'm modifying it based on netflix.zuul of ZuulFilter，It from Spring5.0 Reactor springframework。^~^
+ 5：`global-auth` It's implements similar to SSO，But it also includes Feign Interceptor Function。^~^
+ 6：`repository` and `domain-event` They are in the `Domain Driven Design`，I've only implements simple demo,just like `spring data jpa` and `querydsl jpa` ，`domain-event`  _The code comes from [Spring Courses](https://github.com/eugenp/tutorials)_ 。 ^~^
+ 7：`zipkin xx` just an example。^~^

## 功能示意图（A schematic）:
</br>

![Image text](https://github.com/yugenhai108/spring-cloud/blob/master/gateway-auth.png)

</br>

***

## 链路图(present diagram):
</br>

![Image text](https://github.com/yugenhai108/spring-cloud/blob/master/zipkin-detail.png)

</br>

***

## Hystrix Dashboard </br> 

![Image text](https://github.com/yugenhai108/spring-cloud/blob/master/dashboard.png)

