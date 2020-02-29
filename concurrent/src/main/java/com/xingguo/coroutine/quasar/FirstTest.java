/*
 * Copyright (c) 2020, Beijing Jinhaiqunying, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.coroutine.quasar;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.SuspendableRunnable;

/**
 * FirstTest
 *
 * @author guoxing
 * @date 2020/2/26 10:59 AM
 * @since
 */
public class FirstTest {

    public static void main(String[] args) {
//        startThread();
//        startCoroutine();

    }

    /**
     * 证明gc作为守护线程,且gc运行时间;而且主线程结束后,守护线程并非立即结束,而是也有缓冲时间的
     */
    static void testGcDaemonThread() {
        class Demo {

            @Override
            public void finalize() {
                System.out.println(Thread.currentThread().isDaemon());//验证gc线程是不是daemon
                System.out.println("垃圾被清扫了");
            }
        }
        for (int i = 0; i < 10000000; i++) {
            new Demo();
        }
        for (int i = 0; i < 100000; i++) {
            System.out.println("我是主线程执行");
        }
        System.out.println("main:" + Thread.currentThread().isDaemon());//验证主线程是不是daemon
        System.out.println("主线程死亡");
    }


    static void startCoroutine() {
        long start = System.nanoTime();
        final co.paralleluniverse.strands.concurrent.CountDownLatch latch = new co.paralleluniverse.strands.concurrent.CountDownLatch(1000);

        //使用阻塞队列来获取结果。
        for (int i = 0; i < 1000; i++) {
            //这里的Fiber就是协程
            new Fiber<>((SuspendableRunnable) latch::await).start();
        }

        long time = System.nanoTime() - start;
        //234.066
        System.out.printf("协程 Tasks took %.3f ms to run%n", time / 1e6);
    }


    static void startThread() {
        long start = System.nanoTime();
        final java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1000);

        //使用阻塞队列来获取结果。
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        long time = System.nanoTime() - start;
        //146.828
        System.out.printf("线程 Tasks took %.3f ms to run%n", time / 1e6);
    }
}
