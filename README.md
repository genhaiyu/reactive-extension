# :mushroom: 主流互联网(高并发、高可用)分布式微服务架构

***
##  :blossom: Eureka and Gateway Component </br>
+ 1：Eureka 集群提供微服务注册和发现，Gateway组件提供微服务的安全、鉴权、转发功能。
##  :bouquet: Monitor and Config </br>
+ 1：Monitor 监控各个微服务。包括Turbine聚合微服务、HystrixDashboard监视微服务熔断情况看板等。
+ 2：Config 微服务和配置文件分离，实现每个微服务的单独配置文件热更新。
## :seedling: Product and Customer </br>
+ 1：简单的微服务示例，互相Feign调用，也可以通过Gateway转发。
***

## :partly_sunny: 操作手册 </br>

+ 1：启动 Eureka注册中心（Port = 8700）。
## </br>
+ 2：Config（Port = 8701）需要在gitlab或者github上创建一个项目。把微服务的配置文件放到对应的文件夹里，并在需要热更新的服务pom.xml里增加：</br>
```java
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-client</artifactId>
        </dependency>
```
（1）application-dev.yml文件里增加：
```java
cloud:
  config:
    discovery:
      enabled: true
      service-id: config
```
## </br>
+ 3：启动 Gateway（Port = 8702）。Gateway作用是给敏感请求如登录和鉴权的接口拦截和转发（不通过网关返回，通过则转发到服务上）。</br>
（1）启动 Product （Port = 8704）和启动 Customer（Port = 8705），调用顺序Product -> Cutsomer， 如果是Feign请直接在Product （Port = 8704）访问 http://192.168.0.113:8704/pro/index2/1 </br>
（2）这样服务就打到了Customer（Port = 8705）上，返回 `拿到来自customer的randomId:1146445963213279232` </br>
（3）如果要从网关Gateway（Port = 8702）转发，请用网关Gateway（Port = 8702）地址访问 http://192.168.0.113:8702/customer/cust/getRandomId/1 </br>
（4）页面返回了一个随机数 `1146449893401956352` ，直接从路由转发到Customer（Port = 8705）上，没有封装的前缀语，详情见 [代码](https://github.com/yugenhai108/spring-cloud/blob/master/customer/src/main/java/org/yugh/customer/controller/IndexController.java)。</br>
（5）需要注意，网关Gateway（Port = 8702）只给配置了需要路由的服务和地址生效，见 application-dev.yml配置：</br>
```java
zuul:
  host:
    max-per-route-connections: 20
    max-total-connections: 200
  routes:
    api-product.path: /pro/**
    api-product.serviceId: product
    api-customer.path: /cust/**
    api-customer.serviceId: customer
```
</br>
（6）api-product.path 对应目前@RequestMapping路径，api-customer.serviceId 微服务名字spring:application:name </br> 
## </br>

+ 4：启动Monitor（Port = 8703）。单个服务的hystrix，如 Product （Port = 8704） http://192.168.0.113:8704/actuator/hystrix.stream </br>
（1）在HystrixDashboard看熔断或调用情况，访问 http://192.168.0.113:8703/hystrix ，在链接框输入要监控 Product （Port = 8704）http://192.168.0.113:8704/hystrix.stream ，Delay: 1000ms  Title: http://192.168.0.113:8704/hystrix.stream </br>
（2）请求的熔断成功失败率在 [代码](https://github.com/yugenhai108/spring-cloud/blob/master/product/src/main/java/org/yugh/product/controller/IndexController.java)，请求地址 http://192.168.0.113:8704/pro/index2/1 ，如被调方抛异常也熔断，熔断率可自行控制。</br>
（3）监控图：

![Image text](https://github.com/yugenhai108/spring-cloud/blob/master/hystrixDashboard.png)
