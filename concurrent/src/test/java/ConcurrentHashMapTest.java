/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConcurrentHashMap
 *
 * @author guoxing
 * @date 2020/4/1 5:14 PM
 * @since
 */
public class ConcurrentHashMapTest {
    @Test
    public void testThreadJunit() throws Throwable {

        class HashMapThread extends TestRunnable {
            AtomicInteger ai = new AtomicInteger(0);
            Map<Integer, Integer> map = new HashMap<>(1);

            @Override
            public void runTest() throws Throwable {
                while (ai.get() < 10000) {
                    map.put(ai.get(), ai.get());
                    ai.incrementAndGet();
                }
                System.out.println(Thread.currentThread().getName() + "执行结束完");
            }
        }
        //Runner数组，想当于并发多少个。
        TestRunnable[] trs = new TestRunnable[100];
        for (int i = 0; i < 100; i++) {
            trs[i] = new HashMapThread();
        }

        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 开发并发执行数组里定义的内容
        mttr.runTestRunnables();
    }

}
