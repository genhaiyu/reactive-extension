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

1：`common-auth`无侵入式监听验证用户合法性，`PreAuthAspect`可拦截 org.yugh.** 可替换为公司规范包下所有需要监听的控制器。
</br>
2：`gateway`Spring Cloud Gateway实际应用实现，包括最新的WebFlux，Reactive，以及需要自定义RateLimiter令牌桶限流实现。
</br>
3：启动自己的项目后，从Gateway网关访问验证通过后会生成用户信息的token并转发到自己的项目中。
</br>
4：`common-auth`拦截器解析从`gateway`组件通过的用户token判定是否用的当前加密方式和用户有效期。
</br>
5：架构实际模型
![]()
</br>
6：原始架构参考于gateway-auth.png

***
