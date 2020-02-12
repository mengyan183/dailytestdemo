package com.xingguo.concurrent.reason;

import java.util.concurrent.CompletableFuture;

/**
 * 测试并发产生的原因: 可见性
 * 一个线程对共享变量的改变,其他线程不可见
 * 在一个类中只要有一个被volatile修饰的变量,该类中的所有的变量都会被加载到cpu主内存中去,不会被单独线程独自缓存
 *
 * @author xingguo
 * @date 2020-02-12 10:09 AM
 * @since 1.0.0
 */
public class Visibility {
    private static Visibility visibility = new Visibility();

    /**
     * 1.类被加载的时候，普通方法加载到方法区，静态方法和静态字段加载到方法区中的静态区
     * 2.首先静态字段会进行默认初始化。即 visibility=null       counter=0        stop=false
     * 3:对静态字段进行显示初始化,按照代码自上到下的顺序逐步加载static变量,如果static变量有赋值操作,则执行赋值操作,值为所赋的值;如果没有赋值操作,则当前值为默认初始化的值
     * 4:执行静态代码块
     */
    public Visibility() {
        System.out.println("类加载默认初始化的counter=" + counter + " ; stop=" + stop);
    }

    // counter 被初始化后的数据Reader线程可以读取到,但之后被Writer线程修改后的数据,Reader线程是不可见
    private static int counter;
    // 当一个变量被 volatile 修饰后，表示着线程本地内存无效，当一个线程修改共享变量后他会立即被更新到主内存中，当其他线程读取共享变量时，它会直接从主内存中读取
    // Reader线程可以读取到被Writer线程修改的数据
//    private static volatile int counter;

    // stop被Writer修改后的数据对于Reader来说是不可见的,所以Reader线程启动后进入死循环
    private static boolean stop;

    //    private static volatile boolean stop;
    private static boolean test;

    private static class Reader implements Runnable {
        private volatile int newestCounter;

        @Override
        public void run() {
            System.out.println("循环开始之前的stop " + stop);
            while (!stop) {
                if (newestCounter != counter) {
                    newestCounter = counter;
                    System.out.println("Reader has read a new value=" + newestCounter + ";test=" + test);
                }

            }
            System.out.println("循环结束之后的stop " + stop);
            System.out.println("Reader stopped at:" + System.currentTimeMillis());
        }
    }

    private static class Writer implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                counter = i;
                test = i % 2 == 0;
                System.out.println("writer has write a new value to counter=" + counter + ";test=" + test);
                // 等待 Reader 去读取 counter 的变化
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stop = true;
            System.out.println("Writer set stop at:" + System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
        // 使用并行线程测试可见性操作
        CompletableFuture.allOf(CompletableFuture.runAsync(new Writer()), CompletableFuture.runAsync(new Reader())).join();
    }
}
