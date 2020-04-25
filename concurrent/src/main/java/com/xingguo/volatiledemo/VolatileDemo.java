/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.volatiledemo;

import java.util.concurrent.TimeUnit;

/**
 * VolatileDemo
 * {@link https://mp.weixin.qq.com/s/dHaCuxoPkYUUoyl6g0Ye1A}
 *
 * @author guoxing
 * @date 2020/4/24 7:48 PM
 * @since
 */
public class VolatileDemo {
    private static boolean flag = false;
    //    private static int i = 0;
    // 第三次改造 test1
//    private static volatile int i = 0;
    // 第四次改造 test1
    private static Integer i = 0;

    public static void main(String[] args) {
        test1();
//        test2();
//        test3();

    }

    public static void test3() {
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                flag = true;
                System.out.println("flag 被修改成 true");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag) {
            i++;
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        System.out.println("程序结束,i=" + i);
    }

    public static void test2() {
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                flag = true;
                System.out.println("flag 被修改成 true");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag) {
            i++;
            System.out.println("flag 标识" + flag);
        }
        System.out.println("程序结束,i=" + i);
    }

    public static void test1() {
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                flag = true;
                System.out.println("flag 被修改成 true");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag) {
            i++;
        }
        System.out.println("程序结束,i=" + i);
    }
}
