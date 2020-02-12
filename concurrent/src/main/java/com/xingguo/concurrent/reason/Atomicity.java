package com.xingguo.concurrent.reason;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试并发产生的原因:原子性
 * 由于cpu中线程执行存在时间片,会导致线程切换后导致无法保证原子性
 *
 * @author xingguo
 * @date 2020-02-12 5:04 PM
 * @since 1.0.0
 */
public class Atomicity {

    public static void main(String[] args) throws InterruptedException {
        new Atomicity().unAtomicity();
        new Atomicity().atomicity();
    }

    private int unsafeCounter = 0;

    private void unsafeIncrease() {
        int idx = 0;
        while (idx++ < 10000) {
            unsafeCounter++;
        }
    }

    /**
     * 非原子性操作代码
     * 当多个线程对共享变量进行相同操作时,由于cpu时间片的切换会导致cpu内存中缓存中的数据被错误的覆盖
     *
     * @author xingguo
     * @date 2020-02-12 5:11 PM
     * @since 1.0.0
     */
    public void unAtomicity() throws InterruptedException {

        // 多个线程执行不安全的非原子性操作
        Runnable runnable = this::unsafeIncrease;
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // 输出的结果在 10000 到20000之间 不会等于20000
        System.out.println("unsafeCounter=" + unsafeCounter);
    }


    private AtomicInteger safeCounter = new AtomicInteger(0);

    private void safeIncrease() {
        int idx = 0;
        while (idx++ < 10000) {
            // 保证了在cpu层面是一次操作
            safeCounter.incrementAndGet();
        }
    }

    /**
     * 安全的原子操作
     *
     * @author xingguo
     * @date 2020-02-12 5:22 PM
     * @since 1.0.0
     */
    public void atomicity() throws InterruptedException {
        // 多个线程执行安全的原子性操作
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                safeIncrease();
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // 结果为20000
        System.out.println("safeCounter=" + safeCounter);
    }

    /**
     * 复合原子操作是不是原子性的;在cpu执行层面多个原子操作并不能组合成一个原子操作,所以也不能保证原子性
     */
    private int[] nodes = new int[]{1, 2};
    private AtomicInteger nodeIndex = new AtomicInteger(0);

    private void unsafeAtomic() {
        int i = 0;
        while (i++ < 100) {
            // 获取当前节点的索引，并将索引加1
            int value = nodes[nodeIndex.getAndIncrement()];
            // 如果索引值等于节点的长度了，则设置为0
            nodeIndex.compareAndSet(nodes.length, 0);
            System.out.println("Thread=" + Thread.currentThread().getName() + " current node value=" + value);
        }
    }
}
