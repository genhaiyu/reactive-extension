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


* Identify the user web server servlet or reactive and load the filter and listeners.
* Base extension WebFilter of WebFlux.
* Jetty is used by default and does not support tomcat.
