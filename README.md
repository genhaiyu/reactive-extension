# :sparkles: Coral (珊瑚) 

[![Build Status](https://travis-ci.org/yugenhai/coral.svg?branch=master)](https://travis-ci.org/yugenhai/coral)
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/yugenhai/coral.git/master)
[![GitHub stars](https://img.shields.io/github/stars/yugenhai/coral)](https://github.com/yugenhai/coral/stargazers)

------

| 框架         | 版本      |
| ------------ | --------- |
| JDK          | 1.8+      |
| Servlet      | 4.0.x     |
| Spring       | 5.2.x     |
| SpringBoot   | 2.2.x     |
| Spring Cloud | Hoxton.x  |
| Jackson      | 2.10.x    |


## 1. 组件使用

#### 使用 maven 命令并编译

```html
cd /coral
mvn clean install -Dmaven.test.skip=true

```
* 编译报错, 检查 <compiler.source> 是否与本机的 jdk 版本一致或调至 1.8

#### 微服务中使用

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

## 2. 组件功能

* **coral-boot, coral-core** 
    * 适配用户容器 Servlet/Reactive 加载拦截器和过滤器;
    * WebFlux 会话信息透传, 线程隔离情况获取当前线程信息;
    * Jetty Reactive Streams HttpClient, WebClient 流式适配器 (待更新);
    * SpringWebFlux 默认加载 Netty, 会有性能问题, 大流量项目要切换到 Tomcat, Jetty, Undertow
    * Jetty 和 WebFlux(Reactor) 已经用在生产环境, 待更新 shutdown container .. 

* **gateway** 
    * Spring Cloud Gateway 入门配置, 如 鉴权/转发/跨域统一设置, 已停更; :smile:
