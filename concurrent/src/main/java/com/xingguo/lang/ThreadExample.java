/*
 * Copyright (c) 2020,guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.lang;

/**
 * ThreadExample
 *
 * @author guoxing
 * @date 2020/4/2 3:07 PM
 * @since
 */
public class ThreadExample {
    public static void main(String[] args) {
        testHowMuchThread();
    }

    public static void testHowMuchThread() {
        System.out.println("helloWorld");
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        int nowThreads = topGroup.activeCount();
        Thread[] lstThreads = new Thread[nowThreads];
        topGroup.enumerate(lstThreads);
        for (int i = 0; i < nowThreads; i++) {
            System.out.println("线程number：" + i + " = " + lstThreads[i].getName());
        }
    }
}
