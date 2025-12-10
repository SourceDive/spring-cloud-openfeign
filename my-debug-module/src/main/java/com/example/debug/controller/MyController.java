package com.example.debug.controller;

import com.example.debug.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FeignClient 测试控制器
 * 
 * 这个控制器专门用于演示 FeignClient 的使用：
 * 1. 通过 @Autowired 注入 HelloClient（Feign 代理对象）
 * 2. 调用 helloClient.getHello() 时，Feign 会发送 HTTP 请求
 * 3. 这是真正的 FeignClient 调用示例
 */
@RestController
public class MyController {

    @Autowired
    private HelloClient helloClient;

    @Value("${server.port:8080}")
    private int serverPort;

    /**
     * 被 FeignClient 调用的目标端点
     * 
     * 注意：这个端点只是为了演示 FeignClient 的调用目标
     * 实际使用中，这个端点应该在另一个服务中
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello from FeignClient target endpoint!";
    }

    /**
     * FeignClient 调用示例
     * 
     * 访问 http://localhost:{port}/test-feign
     * 
     * 这个端点会：
     * 1. 调用 helloClient.getHello() - 这是 FeignClient 调用
     * 2. Feign 会发送 HTTP GET 请求到 http://localhost:8080/hello
     * 3. 返回响应结果
     * 
     * 调试建议：
     * - 在 helloClient.getHello() 这一行打断点
     * - Step Into 跟踪 Feign 的调用链
     */
    @GetMapping("/test-feign")
    public String testFeign() {
        try {
            // ⭐ 这是真正的 FeignClient 调用！
            // 当你调用这个方法时，Feign 会：
            // 1. 拦截这个调用
            // 2. 构建 HTTP 请求（GET http://localhost:8080/hello）
            // 3. 发送 HTTP 请求
            // 4. 解析响应并返回
            String result = helloClient.getHello();
            
            return String.format(
                "✅ FeignClient 调用成功！\n\n" +
                "调用链路：\n" +
                "1. FeignTestController.testFeign()\n" +
                "2. -> helloClient.getHello() [FeignClient 代理调用]\n" +
                "3. -> Feign 发送 HTTP GET http://localhost:8080/hello\n" +
                "4. -> FeignTestController.hello() 处理请求\n" +
                "5. -> 返回结果: %s\n\n" +
                "当前服务器端口: %d\n" +
                "FeignClient 配置的 URL: http://localhost:8080\n\n",
                result, serverPort
            );
        } catch (Exception e) {
            return String.format(
                "❌ FeignClient 调用失败\n\n" +
                "调用链路：\n" +
                "1. FeignTestController.testFeign()\n" +
                "2. -> helloClient.getHello() [FeignClient 代理调用]\n" +
                "3. -> Feign 尝试发送 HTTP GET http://localhost:8080/hello\n" +
                "4. -> ❌ 失败\n\n" +
                "错误信息：\n" +
                "- 错误类型: %s\n" +
                "- 错误详情: %s\n\n" +
                "当前服务器端口: %d\n" +
                "FeignClient 配置的 URL: http://localhost:8080\n\n" +
                "💡 提示：\n" +
                "- 如果端口不匹配，请修改 HelloClient 中的 url\n" +
                "- 在 helloClient.getHello() 打断点可以查看详细的调用栈",
                e.getClass().getSimpleName(),
                e.getMessage(),
                serverPort
            );
        }
    }

    /**
     * 显示 FeignClient 的信息
     * 访问 http://localhost:{port}/feign-info
     */
    @GetMapping("/feign-info")
    public String feignInfo() {
        return String.format(
            "📋 FeignClient 详细信息\n\n" +
            "HelloClient 对象信息：\n" +
            "- 类型: %s\n" +
            "- 是否为代理对象: %s\n" +
            "- 实现的接口: %s\n" +
            "- 当前服务器端口: %d\n\n" +
            "🔍 调试建议 - 在这些地方打断点：\n\n" +
            "1️⃣ FeignClient 创建阶段：\n" +
            "   - FeignClientsRegistrar.registerFeignClients()\n" +
            "   - FeignClientFactoryBean.getObject()\n" +
            "   - FeignContext.get()\n\n" +
            "2️⃣ FeignClient 调用阶段：\n" +
            "   - helloClient.getHello() [在 FeignTestController.testFeign() 中]\n" +
            "   - feign.ReflectiveFeign$FeignInvocationHandler.invoke()\n" +
            "   - LoadBalancerFeignClient.execute()\n" +
            "   - feign.Client.Default.execute()\n\n" +
            "3️⃣ HTTP 请求发送：\n" +
            "   - org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient.execute()\n" +
            "   - com.netflix.client.AbstractLoadBalancerAwareClient.executeWithLoadBalancer()\n\n" +
            "💡 提示：\n" +
            "- 在 FeignTestController.testFeign() 的 helloClient.getHello() 打断点\n" +
            "- 然后 Step Into，就能看到 Feign 的完整调用链",
            helloClient.getClass().getName(),
            java.lang.reflect.Proxy.isProxyClass(helloClient.getClass()),
            helloClient.getClass().getInterfaces()[0].getName(),
            serverPort
        );
    }
    
    /**
     * FeignClient 调用说明
     * 访问 http://localhost:{port}/compare
     */
    @GetMapping("/compare")
    public String compare() {
        return String.format(
            "📊 FeignClient 调用说明\n\n" +
            "1️⃣ 目标端点（FeignTestController.hello()）：\n" +
            "   - 这是被 FeignClient 调用的目标端点\n" +
            "   - 访问: http://localhost:%d/hello\n" +
            "   - 注意：实际使用中，这个端点应该在另一个服务中\n\n" +
            "2️⃣ FeignClient 调用（helloClient.getHello()）：\n" +
            "   - helloClient 是一个 Feign 代理对象\n" +
            "   - 调用 getHello() 会发送 HTTP 请求\n" +
            "   - 这是真正的 FeignClient 使用\n" +
            "   - 访问: http://localhost:%d/test-feign\n\n" +
            "🔑 FeignClient 调用流程：\n" +
            "1. 代码调用 helloClient.getHello()\n" +
            "2. Feign 代理拦截调用\n" +
            "3. Feign 构建 HTTP GET 请求到 http://localhost:8080/hello\n" +
            "4. 发送 HTTP 请求\n" +
            "5. 接收响应并解析\n" +
            "6. 返回结果\n\n" +
            "💡 调试方法：\n" +
            "- 在 helloClient.getHello() 打断点，Step Into\n" +
            "- 你会看到 Feign 的完整调用链",
            serverPort, serverPort
        );
    }
}

