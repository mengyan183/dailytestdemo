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
        startCoroutine();

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
