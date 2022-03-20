# 版本对应

https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E

| Spring Cloud Alibaba Version | Spring Cloud Version     | Spring Boot Version |
| ---------------------------- | ------------------------ | ------------------- |
| 2.2.7.RELEASE                | Spring Cloud Hoxton.SR12 | 2.3.12.RELEASE      |
| 2021.1                       | Spring Cloud 2020.0.1    | 2.4.2               |

| Spring Cloud Alibaba Version | Sentinel Version | Nacos Version | RocketMQ Version | Dubbo Version | Seata Version |
| ---------------------------- | ---------------- | ------------- | ---------------- | ------------- | ------------- |
| 2.2.7.RELEASE*               | 1.8.1            | 2.0.3         | 4.6.1            | 2.7.13        | 1.3.0         |
| 2021.1                       | 1.8.0            | 1.4.1         | 4.4.0            | 2.7.8         | 1.3.0         |

Spring Cloud 与 Spring Boot 对应关系 https://start.spring.io/actuator/info

# 文档

[Spring Cloud Alibaba 参考文档](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html)

D:\GZH\IdeaProjects\SpringCloudAlibabaStudy\doc\Spring Cloud Alibaba 参考文档.pdf

# 演示 Demo

[Sentinel Example](https://github.com/alibaba/spring-cloud-alibaba/tree/master/spring-cloud-alibaba-examples/sentinel-example/sentinel-core-example/readme-zh.md)

[Nacos Config Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)

[Nacos Discovery Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-discovery-example/readme-zh.md)

[RocketMQ Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/rocketmq-example/readme-zh.md)

[Seata Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/seata-example/readme-zh.md)

[Alibaba Cloud OSS Example](https://github.com/alibaba/aliyun-spring-boot/tree/master/aliyun-spring-boot-samples/aliyun-oss-spring-boot-sample)

[Alibaba Cloud SMS Example](https://github.com/alibaba/aliyun-spring-boot/tree/master/aliyun-spring-boot-samples/aliyun-sms-spring-boot-sample)

[Alibaba Cloud SchedulerX Example](https://github.com/alibaba/aliyun-spring-boot)

**demo代码及说明：**D:\GZH\Documents\GitHub\spring-cloud-alibaba\spring-cloud-alibaba-examples

# 项目pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.gzh</groupId>
    <artifactId>SpringCloudAlibabaStudy</artifactId>
    <version>1.0-SNAPSHOT</version>
    <modules>
    </modules>

    <packaging>pom</packaging>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <spring-boot.version>2.3.12.RELEASE</spring-boot.version>
        <spring.cloud.version>Hoxton.SR12</spring.cloud.version>
        <spring-cloud-alibaba.version>2.2.7.RELEASE</spring-cloud-alibaba.version>
<!--        <log4j2.version>2.17.1</log4j2.version>-->
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
```

# Nacos 

## Nacos Server

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

## Nacos provider

### pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### application.yml

```yml
server:
  port: 8311
spring:
  application:
    name: nacos-discovery-provider
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      username: nacos
      password: nacos
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```

### 主启动类

添加`@EnableDiscoveryClient`

### 提供服务

```java
@RestController
public class EchoController {
    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string) {
        return "hello Nacos Discovery " + string;
    }
}
```

### 展示不同端口

```java
@RestController
public class EchoController {
    @Value("${server.port}")
    String port;
    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string) {
        return port + "hello Nacos Discovery " + string;
    }
}
```

通过在application.yml中设置不同端口，打包启动。

#### 坑：

打包前需要在pom.xml中添加

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```



查看 Nacos Endpoint 的信息：http://127.0.0.1:8311/actuator/nacosdiscovery

## Nacos consumer

### Ribbon

#### pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- 此依赖可省略 spring-cloud-starter-alibaba-nacos-discovery默认集成了spring-cloud-starter-netflix-ribbon -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

#### application.yml

```yml
server:
  port: 8321
spring:
  application:
    name: nacos-discovery-comsumer
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        fail-fast: true
      username: nacos
      password: nacos
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```

#### 主启动类

添加`@EnableDiscoveryClient`

#### LoadBalanced

```java
@Configuration
public class BeanConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

#### 消费服务

```java
@RestController
public class EchoController {
    private final RestTemplate restTemplate;
    public EchoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/echo-rest/{str}")
    public String rest(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-discovery-provider/echo/" + str, String.class);
    }
}
```

访问http://127.0.0.1:8321/echo-rest/123会显示“hello Nacos Discovery 123”

### OpenFeign

#### pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

#### application.yml

```yml
server:
  port: 8322
spring:
  application:
    name: nacos-discovery-comsumer-feign
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        fail-fast: true
      username: nacos
      password: nacos
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```

#### 主启动类

添加`@EnableDiscoveryClient`、`@EnableFeignClients`

#### feignclient

```java
@FeignClient(name = "nacos-discovery-provider")
public interface EchoService {
    @GetMapping(value = "/echo/{str}")
    String echo(@PathVariable("str") String str);
}
```

#### 消费服务

```java
@RestController
public class EchoController {
    private final EchoService echoService;
    public EchoController(EchoService echoService) {
        this.echoService = echoService;
    }
    @GetMapping(value = "/echo-feign/{str}")
    public String feign(@PathVariable String str) {
        return echoService.echo(str);
    }
}
```

访问http://127.0.0.1:8322/echo-feign/1234会显示“hello Nacos Discovery 1234”

### LoadBalancer

#### pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

#### application.yml

```yml
server:
  port: 8323
spring:
  application:
    name: nacos-discovery-consumer-sclb
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        fail-fast: true
      username: nacos
      password: nacos
    loadbalancer:
      ribbon:
        enabled: false
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```

注意：spring.cloud.loadbalancer.ribbon.enabled=false

#### 主启动类

添加`@EnableDiscoveryClient`

LoadBalanced

```java
@Configuration
public class BeanConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

#### 消费服务

```java
@RestController
public class EchoController {
    private final RestTemplate restTemplate;
    public EchoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/echo-sclb/{str}")
    public String rest(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-discovery-provider/echo/" + str, String.class);
    }
}
```

访问http://localhost:8323/echo-sclb/12345会显示“hello Nacos Discovery 12345”

## Nacos Config

Nacos Config 目前提供了三种配置能力从 Nacos 拉取相关的配置

- A: 通过 `spring.cloud.nacos.config.shared-dataids` 支持多个共享 Data Id 的配置
- B: 通过 `spring.cloud.nacos.config.ext-config[n].data-id` 的方式支持多个扩展 Data Id 的配置
- C: 通过内部相关规则(应用名、应用名+ Profile )自动生成相关的 Data Id 配置

当三种方式共同使用时，他们的一个优先级关系是:A < B < C

Located property source: 

[

BootstrapPropertySource {name='bootstrapProperties-nacos-config-dev.properties,DEFAULT_GROUP'}, 

BootstrapPropertySource {name='bootstrapProperties-nacos-config.properties,DEFAULT_GROUP'}, 

BootstrapPropertySource {name='bootstrapProperties-nacos-config,DEFAULT_GROUP'}, 

BootstrapPropertySource {name='bootstrapProperties-ext.yaml,DEFAULT_GROUP'}, 

BootstrapPropertySource {name='bootstrapProperties-share.yml,DEFAULT_GROUP'}

]

访问http://localhost:8331/user测试

## Nacos+Gateway

### 运行条件

开启server、provider

server地址为127.0.0.1:8848

privider的spring.application.name为nacos-discovery-provider，提供的接口为@GetMapping("/echo/{string}")

### pom.xml

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### application.yml

```yml
server:
  port: 8341
spring:
  application:
    name: nacos-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      username: nacos
      password: nacos
    gateway:
      routes:
        -
          id: nacos-route
          uri: lb://nacos-discovery-provider
          predicates:
            -
              name: Path
              args[pattern]: /nacos/**
          filters:
            -
              StripPrefix=1
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```

### 测试

访问http://127.0.0.1:8341/nacos/echo/1234

# Sentinel

## Sentinel控制台

### 启动

```shell
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-.jar

测试时用的端口号是8380
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

## Nacos consumer OpenFeign + Sentinel

在Nacos consumer OpenFeign项目的基础上，添加

### pom.xml

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

### application.yml

```yml
feign:
  sentinel:
    enabled: true
spring:
  cloud:
    sentinel:
      transport:
        dashboard: localhost:8380
      eager: true
```

### 增加文件

```java
public class EchoServiceFallbackImpl implements EchoService {
    @Override
    public String echo(@PathVariable("str") String str) {
        return "echo fallback";
    }
}

public class FeignConfig {
    @Bean
    public EchoServiceFallbackImpl echoServiceFallback() {
        return new EchoServiceFallbackImpl();
    }
}
```

### 修改`@FeignClient`

```java
@FeignClient(name = "nacos-discovery-provider", fallback = EchoServiceFallbackImpl.class, configuration = FeignConfig.class)
public interface EchoService {
    @GetMapping(value = "/echo/{str}")
    String echo(@PathVariable("str") String str);
}
```

### 测试

Nacos provider服务正常时，访问http://127.0.0.1:8322/echo-feign/12345，显示“hello Nacos Discovery 12345”

下线Nacos provider服务，再次访问，显示“echo fallback”

## Nacos consumer Ribbon + Sentinel

在Nacos consumer Ribbon项目基础上，添加

### pom.xml

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

### application.yml

```yml
spring:
  cloud:
    sentinel:
      transport:
        dashboard: localhost:8380
      eager: true
```

### 限流、降级

```java
import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

public class ExceptionUtil {
    public static ClientHttpResponse handleException(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException exception) {
        return new SentinelClientHttpResponse("限流");
    }
    public static ClientHttpResponse fallbackException(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException exception) {
        return new SentinelClientHttpResponse("降级");
    }
}
//限流方法为官网文档所写，降级不确定
```

### 在RestTemplate上添加`@SentinelRestTemplate`

```
@Configuration
public class BeanConfig {
    @Bean
    @LoadBalanced
    @SentinelRestTemplate(blockHandlerClass = ExceptionUtil.class, blockHandler = "handleException", fallbackClass = ExceptionUtil.class, fallback = "fallbackException")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

## Nacos consumer LoadBalancer + OpenFeign + Sentinel

### pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

### application.yml

```yml
server:
  port: 8324
spring:
  application:
    name: nacos-discovery-consumer-sclb-feign
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        fail-fast: true
      username: nacos
      password: nacos
    loadbalancer:
      ribbon:
        enabled: false
    sentinel:
      transport:
        dashboard: localhost:8380
      eager: true
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
feign:
  sentinel:
    enabled: true
```

### 主启动类

添加`@EnableDiscoveryClient`和`@EnableFeignClients`

### FeignClient

```java
@FeignClient(name = "nacos-discovery-provider", fallback = EchoServiceFallbackImpl.class, configuration = FeignConfig.class)
public interface EchoService {
    @GetMapping(value = "/echo/{str}")
    String echo(@PathVariable("str") String str);
}
```

### Fallback

```java
public class EchoServiceFallbackImpl implements EchoService {
    @Override
    public String echo(@PathVariable("str") String str) {
        return "sclb feign : echo fallback";
    }
}
```

### configuration

```java
public class FeignConfig {
    @Bean
    public EchoServiceFallbackImpl echoServiceFallbackImpl() {
        return new EchoServiceFallbackImpl();
    }
}
```

### 消费服务

```java
@RestController
public class EchoController {
    private final EchoService echoService;

    public EchoController(EchoService echoService) {
        this.echoService = echoService;
    }

    @GetMapping("/echo-sclb-feign/{str}")
    public String feign(@PathVariable String str) {
        return echoService.echo(str);
    }
}
```

### 测试

Nacos provider服务正常时，访问http://localhost:8324/echo-sclb-feign/12345，会显示“hello Nacos Discovery 12345”

下线Nacos provider服务，再次访问，显示“sclb feign : echo fallback”

## Gateway + Sentinel

### pom.xml

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### application.yml

```yml
server:
  port: 8342
spring:
  application:
    name: nacos-sentinel-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      username: nacos
      password: nacos
    gateway:
      routes:
        - id: nacos-sentinel-route
          uri: lb://nacos-discovery-provider
          predicates:
            - Path=/sengate/**
          filters:
            - StripPrefix=1
    sentinel:
      transport:
        dashboard: localhost:8380
      eager: true
      datasource.ds1.file:
        file: "classpath: api.json"
        ruleType: gw-api-group
      scg:
        fallback:
          mode: response
          response-status: 444
          response-body: 1234
        order: -100
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```

api.json

```json
[
  {
    "apiName": "provider_api",
    "predicateItems": [
      {
        "pattern": "/sengate/echo/**",
        "matchStrategy": 1
      }
    ]
  }
]
```

### 主启动类

添加`@EnableDiscoveryClient`

好像不加也行

### 测试

http://localhost:8342/sengate/echo/11

