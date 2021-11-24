# Reactive Extension Components

[![License](https://img.shields.io/github/license/genhaiyu/reactive-extension)](https://github.com/genhaiyu/reactive-extension/blob/master/LICENSE)

------

| Framework    | Version   |
| ------------ | --------- |
| JDK          | 11 +      |
| Servlet      | 4.0.x     |
| Spring       | 5.2.x     |
| Spring Boot  | 2.2.x     |
| Spring Cloud | Hoxton.x  |


## 1. Functions


* Logging the Web Server servlet or reactive and load the filter and listeners.
* Reactive tracking, reactive publish and subscribe.
* Exclusive the Jetty server, and unSupport Tomcat server.


## 2. Using in the project

#### 1.1 Git clone

```html
https://github.com/yugenhai/reactive-extension.git
```

#### 1.2 Compile

```html
cd /reactive-extension

mvn clean install -Dmaven.test.skip=true
```

#### 1.3 Use in Pom

```html
<parent>
    <groupId>genhaiyu</groupId>
    <artifactId>reactive-extension</artifactId>
    <version>1.1.3-SNAPSHOT</version>
</parent>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>genhaiyu</groupId>
            <artifactId>reactive-extension</artifactId>
            <version>1.1.3-SNAPSHOT</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 3. Changelog

* Created in 2019
* Updated in 2021
