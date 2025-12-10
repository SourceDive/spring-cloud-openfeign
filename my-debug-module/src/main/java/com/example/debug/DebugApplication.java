package com.example.debug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 调试应用入口
 * 用于阅读 Spring Cloud OpenFeign 源码
 * 
 * 关键注解：
 * - @EnableFeignClients: 启用 Feign 客户端扫描和注册
 * - 会扫描当前包及其子包下的 @FeignClient 接口
 */
@SpringBootApplication
@EnableFeignClients
public class DebugApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebugApplication.class, args);
    }
}

