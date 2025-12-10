# My Debug Module

这是一个用于阅读 Spring Cloud OpenFeign 源码的调试模块。

## 文件结构

```
my-debug-module/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/debug/
│   │   │       ├── DebugApplication.java      # Spring Boot 应用入口
│   │   │       └── client/
│   │   │           └── HelloClient.java        # 最简单的 FeignClient 示例
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/example/debug/
│               └── DebugApplicationTest.java   # 测试用例
└── pom.xml
```

## 核心文件说明

### 1. DebugApplication.java

- Spring Boot 应用主类
- 使用 `@EnableFeignClients` 启用 Feign 客户端
- 提供一个简单的 REST 端点 `/hello`

### 2. HelloClient.java

- 最简单的 FeignClient 接口示例
- 使用 `@FeignClient` 注解定义客户端
- 包含一个 `getHello()` 方法调用远程服务

### 3. DebugApplicationTest.java

- 测试 FeignClient 的创建和注入
- 验证代理对象的创建
- 可以在这里打断点，跟踪源码执行流程

## 如何使用

### 编译项目

```bash
./mvnw clean compile -pl my-debug-module -am
```

### 运行测试

```bash
./mvnw test -pl my-debug-module
```

### 运行应用

```bash
./mvnw spring-boot:run -pl my-debug-module
```

## 阅读源码的入口点

1. **@EnableFeignClients** - 查看 `EnableFeignClients.java`
    - 这个注解启用了 Feign 客户端的自动配置
    - 会触发 `FeignClientsRegistrar` 扫描和注册 Feign 客户端

2. **@FeignClient** - 查看 `FeignClient.java`
    - 定义 Feign 客户端的元数据
    - 包括服务名、URL、配置等

3. **FeignClientFactoryBean** - 查看 `FeignClientFactoryBean.java`
    - Feign 客户端的工厂 Bean
    - 负责创建 Feign 客户端代理对象

4. **FeignClientsRegistrar** - 查看 `FeignClientsRegistrar.java`
    - 扫描和注册 Feign 客户端
    - 处理 `@EnableFeignClients` 和 `@FeignClient` 注解

## 调试建议

1. 在 `DebugApplicationTest.testFeignClientCreated()` 方法中打断点
2. 查看 `helloClient` 对象的类型和实现
3. 跟踪 Feign 客户端的创建过程
4. 查看代理对象的 InvocationHandler 实现

## 下一步

- 尝试添加更多的 FeignClient 方法
- 查看 Feign 的编码器、解码器配置
- 了解 Ribbon 负载均衡的集成
- 研究 Hystrix 熔断器的使用


