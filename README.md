# :sparkles: Coral (珊瑚) 

[![Build Status](https://travis-ci.org/yugenhai/coral.svg?branch=master)](https://travis-ci.org/yugenhai/coral)
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/yugenhai/coral.git/master)
[![GitHub stars](https://img.shields.io/github/stars/yugenhai/coral)](https://github.com/yugenhai/coral/stargazers)

------

| 框架         | 版本      |
| ------------ | --------- |
| Servlet      | 4.0.x     |
| Spring       | 5.2.x     |
| SpringBoot   | 2.2.2     |
| Spring Cloud | Hoxton.SR3|
| Jackson      | 2.10.2    |


## 1. 组件使用

#### 使用 maven 命令并编译

```html
cd /coral
mvn clean install -Dmaven.test.skip=true

```
* 编译报错, 检查 <compiler.source> 是否与本机的 jdk 版本一致或调至 1.8

#### 微服务中使用

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

## 2. 组件功能

* **core/boot** 
    * 自动适配用户选择的容器 Servlet/Reactive 加载拦截器和过滤器;
    * WebFlux 会话信息获取并透传 TraceId, 在线程隔离情况下获取当前线程信息;
    * Jackson 2.10.2 Redis RedisSerializer, Redis 最新序列化器;
    * Jetty Reactive Streams HttpClient, WebClient 流式适配器 (待更新);
    * SpringBoot 2.X Jetty/Reactor Netty/Undertow 容器切换对比切换.

* **gateway** 
    * Spring Cloud Gateway 入门配置, 如 鉴权/转发/跨域统一设置, 已停更;


***
