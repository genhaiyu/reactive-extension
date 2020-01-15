# :sparkles: Coral (珊瑚) 

[![Build Status](https://travis-ci.org/yugenhai/coral.svg?branch=master)](https://travis-ci.org/yugenhai/coral)
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/yugenhai108/coral/master)
[![GitHub stars](https://img.shields.io/github/stars/yugenhai/coral)](https://github.com/yugenhai/coral/stargazers)

------

| 框架         | 版本      |
| ------------ | --------- |
| Servlet      | 4.0.x     |
| Spring       | 5.x       |
| SpringBoot   | 2.1.x     |
| Spring Cloud | Greenwich |


# 1. 将以下基础组件 maven-install 后引用就可以使用

```html
<dependency>
    <groupId>org.yugh.coral</groupId>
    <artifactId>core</artifactId>
    <version>${project.version}</version>
</dependency>

 <dependency>
    <groupId>org.yugh.coral</groupId>
    <artifactId>boot</artifactId>
    <version>${project.version}</version>
 </dependency>

```

## 2. 组件的功能
`groupId`：
```text
org.yugh.coral
```

`artifactId`：
```text
parent (统一管理的 SpringBoot/SpringCloud 版本)
core (基础枚举/注解/缓存适配器/Fegin统一工厂管理,待更新)
boot (根据用户选择Servlet/Reactive自动加载拦截器和过滤器/Feign/restTemplate统一使用,
请求拦截或日志, WebFlux 全局透传 MDC, SpringBoot 2.X 下 Jetty/Reactor Netty/ Undertow 生产环境选择 )
gateway (Spring Cloud Gateway 基础建设, 如 鉴权/转发/跨域统一设置)

接下来持续更新
...

```

------
------
------

## 以下代码不再更新 :stuck_out_tongue:

***

coral-customer 和 coral-product 模拟熔断的简单微服务，启动后互相调用，一方设定 hystrix 熔断参数来控制。

~~## 网关权限会话的使用~~
```html
    <dependency>
        <groupId>org.yugh.coral</groupId>
        <artifactId>auth</artifactId>
        <version>1.0.0</version>
    </dependency>
```
使用方式：

* ~~coral-auth 微服务切面编程应用，"aspect" 包下 PreAuthAspect 追踪Web和接口请求的权限和会话有效期，支持服务端和客户端同时生效。~~
</br>

* ~~coral-gateway` Spring Cloud Gateway 应用实现，包含最新的WebFlux，Reactive，IP限流，自定义RateLimiter限流实现 (待补充WebClient)。~~
</br>

***

~~## 网关权限会话的模型~~

![](https://github.com/yugenhai108/coral/blob/master/about/gateway-sso.png)
</br>
***
