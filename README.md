# :sparkles: framework application learning(框架应用和学习)

![](https://img.shields.io/badge/build-success-green.svg) ![](https://img.shields.io/github/stars/yugenhai108/framework-applications) 

------



| 框架         | 版本      |
| ------------ | --------- |
| Servlet      | 4.0.x     |
| Spring       | 5.x       |
| SpringBoot   | 2.1.x     |
| Spring Cloud | Greenwich |


***
引用 common-auth到项目中
```
        <dependency>
            <groupId>org.yugh</groupId>
            <artifactId>common-auth</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
```
使用方式：

1：`common-auth`无侵入式拦截验证用户合法性，请将`PreAuthAspect`的包拦截修改为自己的项目包路径。
</br>
2：启动自己的项目后，从Gateway网关访问验证通过后会生成用户信息的token并转发到自己的项目中。
</br>
3：auth拦截器解析从Gateway组件通过的用户token判定是否用的当前加密方式和用户有效期。

***
