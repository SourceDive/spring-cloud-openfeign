package com.example.debug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调试应用入口
 * 用于阅读 Spring Cloud OpenFeign 源码
 */
@SpringBootApplication
@EnableFeignClients
@RestController
public class DebugApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebugApplication.class, args);
    }

    /**
     * 简单的 REST 端点，用于测试 FeignClient
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Debug Application!";
    }
}

