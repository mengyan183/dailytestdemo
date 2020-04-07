/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.designpattern.singleton;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * SingletonExample
 *
 * @author guoxing
 * @date 2020/4/2 10:42 AM
 * @since
 */
public class SingletonExample {
    private static ExecutorService executorService = new ThreadPoolExecutor(3, 10, 1, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactoryBuilder().setNameFormat("singleton-safe-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());

    // 使用static 保证使用的是同一个引用对象,
    // volatile 保证多核cpu在并发线程下对象的可见性
    private volatile static SingletonExample singletonExample;

    // 表示不能直接被外部实例化
    private SingletonExample() {

    }

    // 提供获取实例化对象的静态方法,
    public static SingletonExample getInstance() {
        // 判断对象是否被实例化,存在竞态条件,会有并发情况
        if (singletonExample == null) {
            // * 1585798979406   singleton-safe-1第一次判空
            //     * 1585798979406   singleton-safe-0第一次判空
            // 结果表示多个线程同时进入
            System.out.println(System.currentTimeMillis() + "   " + Thread.currentThread().getName() + "第一次判空");
            // 在竞争同步资源时,
            synchronized (SingletonExample.class) {
                //1585798979406   singleton-safe-1进入同步代码块
                // 表示在竞争同步资源时,有一个线程竞争成功获取到了锁,进入执行代码块
                //1585798979407   singleton-safe-0进入同步代码块
                // 表示当先进入同步代码块的数据释放锁之后,其他等待线程才进入,由于对象被volatile修饰,所以第一个线程实例化后,再次进入的线程内存中的内存副本已失效,此时从用户内存中可以直接读取到已被实例化的对象,因此不会再次被实例化
                System.out.println(System.currentTimeMillis() + "   " + Thread.currentThread().getName() + "进入同步代码块");
                if (singletonExample == null) {
                    //1585798979407   singleton-safe-1准备执行实例化
                    // 表示此时还没有被实例化因此开始实例化
                    System.out.println(System.currentTimeMillis() + "   " + Thread.currentThread().getName() + "准备执行实例化");
                    singletonExample = new SingletonExample();
                }
            }
        }
        return singletonExample;
    }

    /**
     * 输出结果
     * 1585798979406   singleton-safe-1第一次判空
     * 1585798979406   singleton-safe-0第一次判空
     * 1585798979406   singleton-safe-1进入同步代码块
     * 1585798979407   singleton-safe-1准备执行实例化
     * 1585798979407   singleton-safe-0进入同步代码块
     * 829340220
     * 829340220
     * 829340220
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
        try {
            CompletableFuture.allOf(CompletableFuture.runAsync(new SingletonGetInstance(), executorService), CompletableFuture.runAsync(new SingletonGetInstance(), executorService)).join();
            SingletonExample instance = SingletonExample.getInstance();
            System.out.println(instance != null ? System.identityHashCode(instance) : "未实例化");
        } finally {
            executorService.shutdown();
        }

    }

    static class SingletonGetInstance extends Thread {

        @Override
        public void run() {
            SingletonExample instance = SingletonExample.getInstance();
            System.out.println(instance != null ? System.identityHashCode(instance) : "未实例化");
        }
    }
}
