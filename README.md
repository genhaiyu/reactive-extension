# Reactive Extension Components

[![Build Status](https://travis-ci.org/yugenhai/plugins-project.svg?branch=master)](https://travis-ci.org/yugenhai/plugins-project)
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/yugenhai/plugins-project.git/master)

------

| Framework    | Version   |
| ------------ | --------- |
| JDK          | 11 +      |
| Servlet      | 4.0.x     |
| Spring       | 5.2.x     |
| Spring Boot  | 2.2.x     |
| Spring Cloud | Hoxton.x  |


## 1. Functions


* Identify the user web server servlet or reactive and load the filter and listeners.
* Reactive tracking, reactive publish and subscribe.
* Jetty is used by default and does not support Tomcat.


## 2. SubProject Using

#### 1.1 Use maven compiler command

```html
cd /reactive-extension

mvn clean install -Dmaven.test.skip=true

```

#### 1.2 Parent

```java

<parent>
    <groupId>io.shixinyangyy.reactive</groupId>
    <artifactId>reactive-extension</artifactId>
    <version>1.0.3-SNAPSHOT</version>
</parent>

```

#### 1.3 DependencyManagement

```java

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.shixinyangyy.reactive</groupId>
            <artifactId>reactive-extension</artifactId>
            <version>1.0.3-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

```

#### 1.4 Dependencies, Or just use one of them

```java

<dependencies>
    <dependency>
        <groupId>io.shixinyangyy.reactive</groupId>
        <artifactId>core</artifactId>
        <version>1.0.2-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>io.shixinyangyy.reactive</groupId>
        <artifactId>reactive</artifactId>
        <version>1.0.2-SNAPSHOT</version>
    </dependency>
</dependencies>

```

## 3. Changelog

* Created in 2019
