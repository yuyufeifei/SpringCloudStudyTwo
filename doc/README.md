# 模块功能

## 公共部分

cloud-api-commons

## Eureka

### 注册中心

cloud-eureka-server8411

cloud-eureka-server8412

cloud-eureka-server8413

### 服务提供者

cloud-provider-payment8401

cloud-provider-payment8402

cloud-provider-payment8403

### 服务消费者

cloud-consumer-order80

## ZooKeeper

### 服务提供者

cloud-provider-payment-zk8404

### 服务消费者

cloud-consumer-order-zk80

## Consul2

### 服务提供者

cloud-provider-payment-consul8405

### 服务消费者

cloud-consumer-order-consul80

## OpenFeign

### 服务消费者

cloud-consumer-order-feign80

与cloud-eureka-server、cloud-provider-payment8401、cloud-provider-payment-hystrix8406等配合测试

## Hystrix

### 服务提供者

cloud-provider-payment-hystrix8406

### 监控

cloud-consumer-hystrix-dashboard8421

与cloud-provider-payment-hystrix8406配合测试

## Gateway

cloud-gateway-gateway8448

## Config

### Config服务端

cloud-config-server8431

### Config客户端

cloud-config-client8432

## Bus

复用Config的cloud-config-server8431、cloud-config-client8432

复制一份8432至cloud-config-client8433

## Stream

### 生产者 output

cloud-stream-rabbitmq-provider8451

### 消费者 intput

cloud-stream-rabbitmq-consumer8452

cloud-stream-rabbitmq-consumer8453

## Sleuth+Zipkin

使用这两个演示：cloud-consumer-order80、cloud-provider-payment8401

## Nacos

### Discovery

#### 生产者

cloudalibaba-provider-payment8461

cloudalibaba-provider-payment8462

#### 消费者

cloudalibaba-consumer-order8471

### Config

cloudalibaba-config-client8475

## Sentinel

cloudalibaba-sentinel-service8481

fallback功能测试沿用Nacos Discovery的模块

## Seata

cloudalibaba-seata-business8491、cloudalibaba-seata-storage8492、cloudalibaba-seata-order8493、cloudalibaba-seata-account8494

# ZooKeeper

## 在windows上启动：

1. 把`conf`文件夹下的`zoo_sample.cfg`文件复制一份，改为`zoo.cfg`
2. 启动`bin`文件夹下的`zkServer.cmd`服务端
3. 启动`bin`文件夹下的`zkCli.cmd`客户端

# Consul

## 在windows上启动

1. 管理员身份运行 cmd 切换至consul.exe文件所在文件夹，运行 console.exe agent -dev

2. 浏览器访问http://localhost:8500/

## 问题

org.springframework.web.client.HttpClientErrorException$NotFound: 404 Not Found: [Invalid URL path: if attempting to use the HTTP API, ensure the path starts with '/v1/']

服务消费者访问服务提供者报错，未解决

# Ribbon

Eureka client默认带了Ribbon，但3.1.0没有带

替换默认轮询算法：
1.在主启动类外另建包，添加算法，如随机
2.主启动类添加@RibbonClient注解，name为服务提供者的服务名，configuration指向添加的算法
3.Eureka3.1.0 没有ribbon 使用@LoadBalancerClient

# OpenFeign

？*feign一般用在服务消费者*

```yml
# 开启服务降级 测试时，只开启Eureka服务和此服务
# 访问此服务http://localhost/consumer/payment/2会得到{"code":6000,"msg":"前方拥挤。","data":null}
# 也可开启服务提供者，然后访问timeout
feign:
  circuitbreaker:
    enabled: true
```

# Hystrix

？*hystrix一般用在服务提供者*

服务降级 fallback

服务熔断 默认5秒内20次调用失败，会触发熔断，熔断机制的注解是@HystrixCommand

### 服务降级

设置单个方法的fallback，在方法上添加

```java
@HystrixCommand(fallbackMethod = "paymentHystrixTimeoutFallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
})
```

设置全局fallback，在类上添加

```java
@DefaultProperties(defaultFallback = "globalHystrixFallback")
```

### 服务熔断

```java
/**
 * 服务熔断
 * 注解@HystrixProperty中的参数在HystrixCommandProperties类中可找到
 *
 * 测试：先访问id为负数的链接，会得到fallback中的提示；
 *      失败率达到60%后，再访问id为正数的链接，还会得到fallback中的提示，过一段时间后恢复正常
 */
@HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //失败率达到多少后跳闸
})
```

### dashboard

在监视服务的yml文件中添加

```yml
hystrix:
  dashboard:
    proxy-stream-allow-list: "*"
```

在被监视的服务中添加

```java
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixConfig {
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");//访问路径
        registrationBean.setName("hystrix.stream");
        return registrationBean;
    }

}
```

在监视链接中填http://localhost:8406/actuator/hystrix.stream

# Gateway

```yml
spring:
  cloud:
    gateway:
#      discovery:    #是否与服务发现组件进行结合，通过 serviceId(必须设置成大写) 转发到具体的服务实例。默认为false，设为true便开启通过服务中心的自动根据 serviceId 创建路由的功能。
#        locator:    #路由访问方式：http://Gateway_HOST:Gateway_PORT/大写的serviceId/**，其中微服务应用名默认大写访问。
#          enabled: true
      routes:
        - id: payment_route             #路由的ID，没有固定规则但要求唯一，建议配合服务名
#          uri: http://localhost:8401    #匹配后提供服务的路由地址
          uri: lb://provider-payment    #通过微服务名匹配
          predicates:
            - Path=/payment/**          #断言，路径相匹配的进行路由转发
#            - After=2022-03-11T10:33:32.348+08:00[Asia/Shanghai]   #ZonedDateTime.now()时间之后触发,Before、Between类似
#            - Cookie=username,gzh       #携带cookie为username=gzh的匹配    测试：curl http://localhost:8448/payment/2 --cookie "username=gzh"
#            - Header=X-Request-Id, \d+  #请求头要有X-Request-Id属性并值为整数的正则表达式   测试：curl http://localhost:8448/payment/2 -H "X-Request-Id:11"
#            - Host=**.hostname.org,**.anotherhost.org   #匹配host    测试：curl http://localhost:8448/payment/2 -H "Host:www.hostname.org"
#            - Method=GET    #匹配get请求
#            - Query=username, \d+   #要有参数名username并且值是整数    测试：curl http://localhost:8448/payment/2?username=11
#          filters:    #官网地址：https://docs.spring.io/spring-cloud-gateway/docs/2.2.9.RELEASE/reference/html/#gatewayfilter-factories
#            - AddRequestParameter=X-Request-Id, 123
```

# Config

## server

```yml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://gitee.com/yuyufeifei/springcloudtwo-config.git
#          search-paths: springcloudtwo-config
#      label: master

#测试：在码云上创建仓库springcloudtwo-config，之后建两个文件configserver-dev.yml、configserver-prod.yml
#访问即可看到对应内容 http://192.168.137.1:8431/master/configserver-dev.yml
#                 http://192.168.137.1:8431/master/configserver-prod.yml
#                 http://192.168.137.1:8431/configserver-dev.yml
#                 http://192.168.137.1:8431/configserver-prod.yml
#   访问格式如下：https://docs.spring.io/spring-cloud-config/docs/2.2.8.RELEASE/reference/html/#_quick_start
#     /{application}/{profile}[/{label}]
#     /{application}-{profile}.yml
#     /{label}/{application}-{profile}.yml
#     /{application}-{profile}.properties
#     /{label}/{application}-{profile}.properties
```

## client

自动刷新配置

```yml
management:
  endpoints:
    web:
      exposure:
        include: "*"    #自动刷新配置需添加
```

添加@RefreshScope注解
在gitee修改配置之后，需要用curl命令发送 curl -X POST "http://localhost:8432/actuator/refresh"
然后配置就更新了

**但这并不是最终解决方案** ==>Bus

# Bus

Spring Cloud Bus目前支持RabbitMQ和Kafka

## RabbitMQ安装

### windows安装（未试）

otp_win64_24.3.exe

rabbitmq-server-3.9.13.exe

### docker中运行

```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

访问 http://localhost:15672

账号密码为guest

## 添加Bus和RabbitMQ

在Config项目基础上，server和client添加

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

server的yml添加

```yml
spring:
  rabbitmq:
    host: localhost     #默认就是localhost
    port: 5672
    username: guest     #默认就是guest
    password: guest     #默认就是guest
management:
  endpoints:
    web:
      exposure:
        include: "bus-refresh"
```

client的yml添加

```yml
spring:
  rabbitmq:
    host: localhost     #默认就是localhost
    port: 5672
    username: guest     #默认就是guest
    password: guest     #默认就是guest
```

在gitee修改配置后，用curl命令发送 curl -X POST "http://localhost:8431/actuator/bus-refresh"

配置通过Bus和RabbitMQ更新给所有服务即cloud-config-client8432和cloud-config-client8433

### 只让某一个实例生效而不是全部

curl -X POST "http://localhost:8431/actuator/bus-refresh/{destination}"

通过destination参数类指定需要更新配置的服务或实例

例：destination设置为{spring.application.name}:{server.port}

curl -X POST "http://localhost:8431/actuator/bus-refresh/cloud-config-client:8432"

# Stream

Stream支持RabbitMQ、Kafka

此项目测试演示用的RabbitMQ

# Sleuth

## zipkin

官网：https://github.com/openzipkin/zipkin

The quickest way to get started is to fetch the [latest released server](https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec) as a self-contained executable jar. Note that the Zipkin server requires minimum JRE 8. For example:

```
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

You can also start Zipkin via Docker.

```
# Note: this is mirrored as ghcr.io/openzipkin/zipkin
docker run -d -p 9411:9411 openzipkin/zipkin
```

Once the server is running, you can view traces with the Zipkin UI at `http://your_host:9411/zipkin/`.

## 演示

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

```yml
spring:
  zipkin:
    base-url: http://localhost:9411/    #默认就是这个
  sleuth:
    sampler:
      probability: 1    #采样率值介于0-1之间，1表示全部采集
```

服务之间调用后，通过localhost:9411查看

# Spring Cloud Alibaba

## 版本说明

[版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)（2022-3-15）

| Spring Cloud Alibaba Version | Spring Cloud Version     | Spring Boot Version |
| ---------------------------- | ------------------------ | ------------------- |
| 2021.0.1.0                   | Spring Cloud 2021.0.1    | 2.6.3               |
| 2.2.7.RELEASE                | Spring Cloud Hoxton.SR12 | 2.3.12.RELEASE      |
| 2021.1                       | Spring Cloud 2020.0.1    | 2.4.2               |

| Spring Cloud Alibaba Version | Sentinel Version | Nacos Version | RocketMQ Version | Dubbo Version | Seata Version |
| ---------------------------- | ---------------- | ------------- | ---------------- | ------------- | ------------- |
| 2021.0.1.0*                  | 1.8.3            | 1.4.2         | 4.9.2            | 2.7.15        | 1.4.2         |
| 2.2.7.RELEASE                | 1.8.1            | 2.0.3         | 4.6.1            | 2.7.13        | 1.3.0         |
| 2021.1                       | 1.8.0            | 1.4.1         | 4.4.0            | 2.7.8         | 1.3.0         |

## Nacos Server

Nacos支持AP和CP模式的切换

### 获取Nacos Server

[Nacos Server 下载页](https://github.com/alibaba/nacos/releases) 

### 启动

#### Linux/Unix/Mac

启动命令(standalone代表着单机模式运行，非集群模式):

```
sh startup.sh -m standalone
```

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：

```
bash startup.sh -m standalone
```

#### Windows

启动命令(standalone代表着单机模式运行，非集群模式):

```
startup.cmd -m standalone
```

### 查看

```
INFO Tomcat started on port(s): 8848 (http) with context path '/nacos'
```

浏览器访问http://127.0.0.1:8848/nacos/index.html默认账号名/密码为 nacos/nacos

### 集群部署

https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html

## Nacos文档
https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html

## Nacos Config

dataId 的完整格式如下：
```
${prefix}-${spring.profiles.active}.${file-extension}
```

- prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
- spring.profiles.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。 注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}
- file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。目前只支持 properties 和 yaml 类型。

通过 Spring Cloud 原生注解 @RefreshScope 实现配置自动更新

## Sentinel控制台

### 启动

```shell
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-.jar

测试时用的端口号是8480
java -Dserver.port=8480 -Dcsp.sentinel.dashboard.server=localhost:8480 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.1.jar
```

从 Sentinel 1.6.0 开始，Sentinel 控制台支持简单的**登录**功能，默认用户名和密码都是 `sentinel`。用户可以通过如下参数进行配置：

- `-Dsentinel.dashboard.auth.username=sentinel` 用于指定控制台的登录用户名为 `sentinel`；
- `-Dsentinel.dashboard.auth.password=123456` 用于指定控制台的登录密码为 `123456`；如果省略这两个参数，默认用户和密码均为 `sentinel`；
- `-Dserver.servlet.session.timeout=7200` 用于指定 Spring Boot 服务端 session 的过期时间，如 `7200` 表示 7200 秒；`60m` 表示 60 分钟，默认为 30 分钟；

### [配置控制台信息](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html#_配置控制台信息)

application.yml

```
spring:
  cloud:
    sentinel:
      transport:
        port: 8719
        dashboard: localhost:8080
```

这里的 `spring.cloud.sentinel.transport.port` 端口配置会在应用对应的机器上启动一个 Http Server，该 Server 会与 Sentinel 控制台做交互。比如 Sentinel 控制台添加了1个限流规则，会把规则数据 push 给这个 Http Server 接收，Http Server 再将规则注册到 Sentinel 中。

更多 Sentinel 控制台的使用及问题参考： [Sentinel控制台](https://github.com/alibaba/Sentinel/wiki/控制台)

## Sentinel

### @SentinelResource

fallback负责业务异常

blockHandler负责Sentinel控制台配置的规则导致的异常

[详细文档](https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81)

### Sentinel+OpenFeign

个人感觉：

OpenFeign用于服务提供者不可用时，返回友好提示，在服务消费者使用。

### 持久化

yml中添加

```yml
spring:
  cloud:
    sentinel:
      datasource:     #持久化
        ds1:
          nacos:    # 在nacos配置管理中增加配置Data ID为sentinel-service，Group为DEFAULT_GROUP，配置格式为json
            server-addr: localhost:8848
            data-id: ${spring.application.name}
            group-id: DEFAULT_GROUP
            data-type: json
            rule-type: flow
```

nacos中配置的内容为

```json
[
    {
        "resource": "/test2",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]
# resource：资源名称
# limitApp：来源应用
# grade：阈值类型：0-线程数；1-QPS
# count：单机阈值
# strategy：流控模式：0-直接；1-关联；2-链路
# controlBehavior：流控效果：0-快速失败；1-Warm Up；2-排队等待
# clusterMode：是否集群
```

## RocketMQ

示例：

https://github.com/fangjian0423/rocketmq-binder-demo

https://github.com/alibaba/spring-cloud-alibaba/tree/2.2.x/spring-cloud-alibaba-examples/rocketmq-example

## Seata

https://github.com/seata/seata

https://seata.io/zh-cn/

### Server

https://seata.io/zh-cn/docs/ops/deploy-guide-beginner.html

#### 下载

https://github.com/seata/seata/releases

这里使用1.4.2版本

#### 脚本

已下载github上的文件seata-develop.zip

https://github.com/seata/seata/blob/develop/script

包含[数据库脚本](https://github.com/seata/seata/blob/develop/script/server/db/mysql.sql)和[config.txt](https://github.com/seata/seata/blob/develop/script/config-center/config.txt)、[nacos-config.sh](https://github.com/seata/seata/blob/develop/script/config-center/nacos/nacos-config.sh)。注意：nacos-config.sh要在config.txt的下一级目录。从v1.4.2版本开始，已支持从一个Nacos dataId中获取所有配置信息,你只需要额外添加一个dataId配置项。(本次测试使用dataId方式)

#### 修改配置

##### Server高可用

Seata-Server 需要使用注册中心，并把事务数据保存到数据库中，以 Nacos 为例

- 修改`registry.conf`的注册中心配置

```
registry {
  type = "nacos"

  nacos {
    application = "seata-server"
    serverAddr = "127.0.0.1:8848"
    group = "SEATA_GROUP"
    namespace = ""
    cluster = "default"
    username = ""
    password = ""
  }
}

config {
  type = "nacos"

  nacos {
    serverAddr = "127.0.0.1:8848"
    namespace = ""
    group = "SEATA_GROUP"
    username = "nacos"
    password = "nacos"
    dataId = "seataServer.properties"
  }
}
```

- 将config.txt中的内容添加进Nacos配置中心。Data ID:seataServer.properties、Group:SEATA_GROUP。
- 需要修改配置中心的以下几个配置(含db与redis,二者选其一 注:redis需seata-server 1.3版本及以上)

```
service.vgroupMapping.my_test_tx_group=default
store.mode=db|redis
-----db-----
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true
store.db.user=root
store.db.password=123456
----redis----
store.redis.host=127.0.0.1
store.redis.port=6379
store.redis.maxConn=10
store.redis.minConn=1
store.redis.database=0
store.redis.password=null
store.redis.queryLimit=100
```

- db模式需要在数据库创建 `global_table`, `branch_table`, `lock_table`表

相应的脚本在GitHub 的 [/script/server/db/](https://github.com/seata/seata/tree/develop/script/server/db) 目录下

这样，启动多个seata-server，即可实现其高可用

### 测试项目配置

示例代码参考[官方示例](https://github.com/alibaba/spring-cloud-alibaba/tree/2.2.x/spring-cloud-alibaba-examples/seata-example)

#### 创建 undo_log 表

[Seata AT 模式](file:/D:/GZH/Documents/GitHub/spring-cloud-alibaba/spring-cloud-alibaba-examples/seata-example/) 需要使用到 undo_log 表。

```$sql
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

#### 创建 示例中 业务所需要的数据库表

```$sql
DROP TABLE IF EXISTS `storage_tbl`;
CREATE TABLE `storage_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`commodity_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `order_tbl`;
CREATE TABLE `order_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT 0,
  `money` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `account_tbl`;
CREATE TABLE `account_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `money` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### 启动Server

windows：双击运行`bin`目录下的`seata-server.bat`

#### application.yml

```yml
seata:
  enabled: true
  tx-service-group: default_tx_group
  service:
    vgroup-mapping:
      default_tx_group: default
    grouplist:
      default: 127.0.0.1:8091
    disable-global-transaction: false
  config:
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      group : "SEATA_GROUP"
      namespace: ""
      dataId: "seataServer.properties"
      username: "nacos"
      password: "nacos"
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: 127.0.0.1:8848
      group : "SEATA_GROUP"
      namespace: ""
      username: "nacos"
      password: "nacos"
```

注：seata.tx-service-group=**default_tx_group**、seata.service.vgroup-mapping.**default_tx_group**=default与Server端配置中心`seataServer.properties`文件中的service.vgroupMapping.**default_tx_group**=default 这三个位置的default_tx_group要相同。

#### 测试

访问：http://localhost:8491/seata/feign

