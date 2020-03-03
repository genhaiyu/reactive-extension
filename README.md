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


## 1. 组件使用

#### 使用 maven 命令并编译

```html
cd /coral
mvn clean install -Dmaven.test.skip=true

```

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

* **boot** 
    * 自动适配用户选择的容器 Servlet/Reactive 加载拦截器和过滤器;
    * WebFlux 会话信息获取, 在线程隔离情况下获取当前线程信息;
    * WebFlux 全局 TraceId/MDC;
    * SpringBoot 2.X Jetty/Reactor Netty/Undertow 请求容器统一激活切换.

* **gateway** 
    * Spring Cloud Gateway 入门配置, 如 鉴权/转发/跨域统一设置, 已停更;


------

***
