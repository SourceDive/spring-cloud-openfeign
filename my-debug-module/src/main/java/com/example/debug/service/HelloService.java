package com.example.debug.service;

import com.example.debug.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Hello 服务类
 * 
 * 这个服务类展示了在实际业务代码中如何使用 FeignClient：
 * - 通过 @Autowired 注入 FeignClient
 * - 在业务方法中调用 FeignClient 的方法
 * - FeignClient 会自动处理 HTTP 请求的发送和响应解析
 */
@Service
public class HelloService {

    @Autowired
    private HelloClient helloClient;

    /**
     * 通过 FeignClient 获取 Hello 消息
     * 
     * 这个方法展示了 FeignClient 的实际使用场景：
     * 1. 调用 helloClient.getHello() 时，Feign 会发送 HTTP 请求
     * 2. 这是真正的远程调用，不是本地方法调用
     * 3. 可以在 helloClient.getHello() 这一行打断点，跟踪 Feign 的调用过程
     * 
     * @return Hello 消息
     */
    public String getHelloMessage() {
        // ⭐ 这是真正的 FeignClient 调用！
        // 虽然看起来像普通的方法调用，但实际上：
        // 1. helloClient 是一个 JDK 动态代理对象
        // 2. 调用 getHello() 会被代理拦截
        // 3. Feign 会构建 HTTP 请求并发送
        // 4. 返回 HTTP 响应的内容
        return helloClient.getHello();
    }
}

