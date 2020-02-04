# :sparkles: Coral (珊瑚) 

[![Build Status](https://travis-ci.org/yugenhai/coral.svg?branch=master)](https://travis-ci.org/yugenhai/coral)
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/yugenhai/coral.git/master)
[![GitHub stars](https://img.shields.io/github/stars/yugenhai/coral)](https://github.com/yugenhai/coral/stargazers)

------

| 框架         | 版本      |
| ------------ | --------- |
| Servlet      | 4.0.x     |
| Spring       | 5.x       |
| SpringBoot   | 2.1.x     |
| Spring Cloud | Greenwich |


## 1. 基础组件使用

#### 使用 maven 命令顺序编译 mvn clean install -Dmaven.test.skip=true

```html
<dependency>
    <groupId>org.yugh.coral</groupId>
    <artifactId>core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

 <dependency>
    <groupId>org.yugh.coral</groupId>
    <artifactId>boot</artifactId>
    <version>1.0.0-SNAPSHOT</version>
 </dependency>

```

## 2. 基础组件功能

* **core**
    * 公用的基础枚举/注解, 缓存适配器, 请求管理的工厂统一配置;

* **boot** 
    * 自动适配用户选择的容器 Servlet/Reactive 加载拦截器和过滤器;
    * WebFlux 下的 RequestContextHolder, 见 `CustomRequestContextHolder`;
    * WebFlux 下全局 traceId/MDC, 见 `CustomRequestContextFilter`;
    * SpringBoot 2.X 下 Jetty/Reactor Netty/Undertow 切换及配置值, 见 `BootContextConfig`, 微服务通过 metadata 选择容器和配置初始值;

* **gateway** 
    * Spring Cloud Gateway 入门配置, 如 鉴权/转发/跨域统一设置, 已停更/废弃;

...

------

## 3. 已废弃的组件 :stuck_out_tongue:

~~auth/coral-gateway~~

***

废弃的网关校验模型见图片组 [这里](https://github.com/yugenhai108/coral/blob/master/about)

***
