package com.example.debug.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 最简单的 FeignClient 示例
 * 用于理解 FeignClient 的工作原理
 */
@FeignClient(name = "localapp", url = "http://localhost:8080")
public interface HelloClient {

    /**
     * 调用远程服务的 /hello 接口
     */
    @GetMapping("/hello")
    String getHello();
}

