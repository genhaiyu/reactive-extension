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
#### Dependencies

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

* **Coral-boot, Coral-core** 
    * Identify the user web server servlet or reactive and load the filter and listeners.
    * WebFlux session entry exit information is passed through, and the current session container information can be obtained in the case of thread isolation.
    * Webflux loads netty by default. There will be performance problems. You need to use jetty or undertow.
    * Jetty and Webflux have used production environment, GracefulShutdownJetty Still under verification.