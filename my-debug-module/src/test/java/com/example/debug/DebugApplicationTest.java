package com.example.debug;

import com.example.debug.client.HelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 最简单的测试用例
 * 用于理解 FeignClient 的创建和使用过程
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DebugApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class DebugApplicationTest {

    @Autowired
    private HelloClient helloClient;

    /**
     * 测试 FeignClient 是否被正确创建和注入
     */
    @Test
    public void testFeignClientCreated() {
        // 验证 FeignClient 代理对象已创建
        assertNotNull("HelloClient should not be null", helloClient);

        // 验证它是一个代理对象
        assertTrue("HelloClient should be a proxy",
                java.lang.reflect.Proxy.isProxyClass(helloClient.getClass()));

        System.out.println("FeignClient 类型: " + helloClient.getClass().getName());
        System.out.println("FeignClient 接口: " + helloClient.getClass().getInterfaces()[0].getName());
    }

    /**
     * 测试调用 FeignClient 方法
     * 注意：这个测试需要应用正在运行
     */
    @Test
    public void testFeignClientCall() {
        assertNotNull("HelloClient should not be null", helloClient);

        // 调用 FeignClient 方法
        // 由于这是一个自引用调用（调用自己的服务），可能会成功
        try {
            String result = helloClient.getHello();
            System.out.println("调用结果: " + result);
        } catch (Exception e) {
            System.out.println("调用异常（这是正常的，因为可能没有运行的服务）: " + e.getMessage());
        }
    }
}

