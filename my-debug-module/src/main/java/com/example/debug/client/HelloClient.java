package com.example.debug.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * FeignClient 接口定义
 * 
 * 这是真正的 FeignClient，它会：
 * 1. 在 Spring 启动时被扫描到（通过 @EnableFeignClients）
 * 2. 被 FeignClientFactoryBean 创建为代理对象
 * 3. 当你调用 getHello() 时，会通过 HTTP 请求调用 http://localhost:8080/hello
 * 
 * 关键点：
 * - @FeignClient 注解告诉 Spring 这是一个 Feign 客户端
 * - url = "http://localhost:8080" 指定了要调用的服务地址
 * - @GetMapping("/hello") 定义了 HTTP 方法和路径
 * - 接口方法会被转换为实际的 HTTP 请求
 */
@FeignClient(name = "localapp", url = "http://localhost:8080")
public interface HelloClient {

    /**
     * 通过 FeignClient 调用远程服务的 /hello 接口
     * 
     * 这个方法会被 Feign 转换为：
     * GET http://localhost:8080/hello
     * 
     * 调用链路：
     * 1. helloClient.getHello() 
     * 2. -> Feign 代理对象拦截调用
     * 3. -> LoadBalancerFeignClient.execute()
     * 4. -> 发送 HTTP GET 请求到 http://localhost:8080/hello
     * 5. -> 返回响应结果
     */
    @GetMapping("/hello")
    String getHello();
}
