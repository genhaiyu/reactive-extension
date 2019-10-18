# :sparkles: Coral (珊瑚)

![](https://img.shields.io/badge/build-success-green.svg) ![](https://img.shields.io/github/stars/yugenhai108/coral) 

------



| 框架         | 版本      |
| ------------ | --------- |
| Servlet      | 4.0.x     |
| Spring       | 5.x       |
| SpringBoot   | 2.1.x     |
| Spring Cloud | Greenwich |


***
## 网关权限会话的使用
```html
    <dependency>
        <groupId>org.yugh.coral</groupId>
        <artifactId>auth</artifactId>
        <version>1.0.0</version>
    </dependency>
```
使用方式：

* `coral-auth` 微服务切面编程应用，"aspect" 包下 PreAuthAspect 追踪Web和接口请求的权限和会话有效期，支持服务端和客户端同时生效。
</br>

* `coral-gateway` Spring Cloud Gateway 应用实现，包含最新的WebFlux，Reactive，IP限流，自定义RateLimiter限流实现 (待补充WebClient)。
</br>

***

## 网关权限会话的模型

![](https://github.com/yugenhai108/coral/blob/master/about/gateway-sso.png)
</br>
***
