/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.stack;

import org.junit.Test;

import java.util.Random;

/**
 * BrowserStackDemoTest
 *
 * @author guoxing
 * @date 2020/4/18 9:08 PM
 * @since
 */
public class BrowserStackDemoTest {
    @Test
    public void browserStackDemoTest() {
        BrowserStackDemo browserStackDemo = new BrowserStackDemo();
        Random random = new Random();
        System.out.println("打开新的页面");
        // 打开新的页面
        for (int i = 0; i < 10; i++) {
            int newPage = random.nextInt(50);
            browserStackDemo.createNewPage(newPage + "");
            System.out.println(newPage);
        }

        // 后退
        int randomBack = random.nextInt(50);
        System.out.println("回退页面");
        for (int i = 0; i < (randomBack < 1 ? 0 : randomBack); i++) {
            String back = browserStackDemo.back();
            if (back == null) {
                break;
            }
            System.out.println(back);
        }

        // 前进
        int randomForward = random.nextInt(50);
        System.out.println("前进页面");
        for (int i = 0; i < (randomForward < 1 ? 0 : randomBack); i++) {
            String forward = browserStackDemo.forward();
            if (forward == null) {
                break;
            }
            System.out.println(forward);
        }
        browserStackDemo.createNewPage("新的页面");
        System.out.println("前进" + browserStackDemo.forward());
    }
}
