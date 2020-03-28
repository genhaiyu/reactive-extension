# :sparkles: Coral (珊瑚) 

[![Build Status](https://travis-ci.org/yugenhai/coral.svg?branch=master)](https://travis-ci.org/yugenhai/coral)
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/yugenhai/coral.git/master)
[![GitHub stars](https://img.shields.io/github/stars/yugenhai/coral)](https://github.com/yugenhai/coral/stargazers)

------

| Framework    | Version   |
| ------------ | --------- |
| JDK          | 1.8+      |
| Servlet      | 4.0.x     |
| Spring       | 5.2.x     |
| SpringBoot   | 2.2.x     |
| Spring Cloud | Hoxton.x  |
| Jackson      | 2.10.x    |


## 1. Using component

#### Use maven compiler

```html
cd /coral
mvn clean install -Dmaven.test.skip=true

```
* 编译报错, 检查 <compiler.source> 是否与本机的 jdk 版本一致或调至 1.8

#### Dependencies

```java

<!-- core boot -->
<dependencies>
    <dependency>
        <groupId>org.yugh.coral</groupId>
        <artifactId>core</artifactId>
        <version>1.0.2-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>org.yugh.coral</groupId>
        <artifactId>boot</artifactId>
        <version>1.0.2-SNAPSHOT</version>
    </dependency>
</dependencies>

```

## 2. Functions of components

* **coral-boot, coral-core** 
    * 适配用户容器 Servlet/Reactive 加载拦截器和过滤器.
    * WebFlux 会话入口出口信息透传, 线程隔离情况获取当前线程信息, 后期会结合 WebClient.
    * SpringWebFlux 默认加载 Netty, 会有性能问题, 大流量项目要切换到 Jetty, Undertow.
    * Jetty 和 WebFlux(Reactor) 已经用在生产环境, GracefulShutdownJetty 正常停止 Jetty 容器待验证.

* **Spring WebFlux**
![WebFlux](https://github.com/yugenhai/coral/blob/master/about/spring-webflux.png)

* **gateway** 
    * Spring Cloud Gateway 入门 Demo, 如 鉴权/转发/跨域统一设置, 已停更.
