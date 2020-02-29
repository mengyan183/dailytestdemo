/*
 * Copyright (c) 2020, Beijing Jinhaiqunying, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.concurrent.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * ThreadMxBeanDetection
 *  死锁监测
 * @author guoxing
 * @date 2020/2/27 5:40 PM
 * @since
 */
public class ThreadMxBeanDetection {
    private static Object object1 = new Object();
    private static Object object2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            synchronized (object1) {
                System.out.println(Thread.currentThread().getName() + " object1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object2) {
                    System.out.println(Thread.currentThread().getName() + " object2");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (object2) {
                System.out.println(Thread.currentThread().getName() + " object2");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object1) {
                    System.out.println(Thread.currentThread().getName() + " object1");
                }
            }
        });
        thread1.start();
        thread2.start();

        // 等待死锁发生
        TimeUnit.SECONDS.sleep(2);

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (null != deadlockedThreads && deadlockedThreads.length > 0) {
            for (long deadlockedThread : deadlockedThreads) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThread);
                System.out.println("found deadlock thread: " + threadInfo.getThreadName());
            }
        }
    }

}
