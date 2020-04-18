/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.stack;

import org.junit.Test;

import java.util.Random;

/**
 * LinkedStackTest
 *
 * @author guoxing
 * @date 2020/4/18 6:48 PM
 * @since
 */
public class LinkedStackTest {
    @Test
    public void linkedStack() {
        LinkedStack<Object> objectLinkedStack = new LinkedStack<Object>();
        Random random = new Random(10);
        System.out.println("入栈");
        for (int i = 0; i < 10; i++) {
            int randomInt = random.nextInt(10);
            objectLinkedStack.in(randomInt);
            System.out.println(randomInt);
        }
        System.out.println("=========================");
        System.out.println("出栈");
        while (true) {
            Object pop = objectLinkedStack.pop();
            if (pop == null) {
                break;
            }
            System.out.println(pop);
        }
    }
}
