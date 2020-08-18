# Reactive Extension Components

[![Build Status](https://travis-ci.org/yugenhai/reactive-extension.svg?branch=master)](https://travis-ci.org/yugenhai/reactive-extension)
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/yugenhai/reactive-extension.git/master)

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


## 2. SubProjects Using

#### 1.1 Git clone

```html

https://github.com/yugenhai/reactive-extension.git

```

#### 1.2 Use maven compiler

```html
cd /reactive-extension

mvn clean install -Dmaven.test.skip=true

```

#### 1.3 Parent

```java

<parent>
    <groupId>io.shixinyangyy.reactive</groupId>
    <artifactId>reactive-extension</artifactId>
    <version>1.0.3-SNAPSHOT</version>
</parent>

```

#### 1.4 DependencyManagement

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

#### 1.5 Dependencies, Or just use one of them

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
