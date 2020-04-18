/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.stack;

import org.junit.Test;

import java.util.Random;

/**
 * ArrayStackTest
 *
 * @author guoxing
 * @date 2020/4/18 3:02 PM
 * @since
 */
public class ArrayStackTest {
    @Test
    public void testArrayStack() {
        ArrayStack arrayStack = new ArrayStack(10);
        Random random = new Random();
        while (true) {
            Object in = arrayStack.in(random.nextInt());
            if (in == null) {
                break;
            }
            System.out.println("入栈  : " + in);
        }
        while (true) {
            Object pop = arrayStack.pop();
            if (pop == null) {
                break;
            }
            System.out.println(pop);
        }
    }
}
