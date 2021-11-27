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
https://github.com/genhaiyu/reactive-extension.git
```

#### 1.2 Compile

```html
cd /reactive-extension

mvn clean install
```

#### 1.3 Use in the pom.xml

```html
    <dependencies>
        <dependency>
            <groupId>genhaiyu</groupId>
            <artifactId>basement</artifactId>
            <version>1.1.2-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>genhaiyu</groupId>
            <artifactId>reactive</artifactId>
            <version>1.1.2-SNAPSHOT</version>
        </dependency>
    <dependencies>
```

## 3. Changelog

* Created in 2019
* Updated in 2021