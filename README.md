# The Spring Boot Extended Plugins

[![Build Status](https://travis-ci.org/yugenhai/plugins-project.svg?branch=master)](https://travis-ci.org/yugenhai/plugins-project)
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/yugenhai/plugins-project.git/master)
[![GitHub stars](https://img.shields.io/github/stars/yugenhai/plugins-project)](https://github.com/yugenhai/plugins-project/stargazers)

------

| Framework    | Version   |
| ------------ | --------- |
| JDK          | 11 +      |
| Servlet      | 4.0.x     |
| Spring       | 5.2.x     |
| Spring Boot  | 2.2.x     |
| Spring Cloud | Hoxton.x  |
| Jackson      | 2.11.x    |


## 1. Using

#### Use maven compiler

```html
cd /plugins-project
mvn clean install -Dmaven.test.skip=true

```
#### Use maven dependency

```java

<dependencies>
    <dependency>
        <groupId>io.genhai.plugins</groupId>
        <artifactId>core</artifactId>
        <version>1.0.2-SNAPSHOT</version>
    </dependency>
    <dependency>
       <groupId>io.genhai.plugins</groupId>
       <artifactId>reactive</artifactId>
       <version>1.0.2-SNAPSHOT</version>
   </dependency>
</dependencies>

```

## 2. Functions


* Identify the user web server servlet or reactive and load the filter and listeners (适配用户 Web 容器 Servlet Reactive 加载过滤器。).
* Base extension WebFilter of WebFlux (WebFlux 会话入口信息透传。).
* (WebFlux 默认加载 Netty 容器, 会有性能问题, 本项目默认加载 Jetty 容器, 并且不支持 Tomcat 容器。).
* (Jetty 和 WebFlux 部分已经用在生产环境, GracefulShutdownJetty 正常停止 Jetty 容器。).
* (RSocket 异步反应式调用 server - client。).