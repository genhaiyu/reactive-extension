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
#### Maven Dependencies

```java

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


* Identify the user web server servlet or reactive and load the filter and listeners (适配用户 Web 容器 Servlet Reactive 加载过滤器。).
* Base extension WebFilter of WebFlux (WebFlux 会话入口信息透传。).
* (WebFlux 默认加载 Netty 容器, 会有性能问题, 本项目默认加载 Jetty 容器, 并且不支持 Tomcat 容器。).
* (Jetty 和 WebFlux 部分已经用在生产环境, GracefulShutdownJetty 正常停止 Jetty 容器。).
* (增加 RSocket 异步响应式调用。)