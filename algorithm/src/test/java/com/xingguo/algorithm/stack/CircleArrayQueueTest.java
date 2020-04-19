/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.stack;

import com.xingguo.algorithm.queue.ArrayQueue;
import com.xingguo.algorithm.queue.CircleArrayQueue;
import org.junit.Test;

/**
 * CircleArrayQueueTest
 *
 * @author guoxing
 * @date 2020/4/19 3:41 PM
 * @since
 */
public class CircleArrayQueueTest {

    @Test
    public void arrayQueueTest() {
        CircleArrayQueue circleArrayQueue = new CircleArrayQueue(10);
        for (int i = 0; i < 10; i++) {
            boolean b = circleArrayQueue.enQueue(String.valueOf(i));
            if (!b) {
                break;
            }
        }
        System.out.println("出队开始");
        for (int i = 0; i < 5; i++) {
            System.out.println(circleArrayQueue.dequeue());
        }
        System.out.println("出队结束,再次入队");
        int num = 0;
        while (true) {
            boolean b = circleArrayQueue.enQueue(String.valueOf(num++));
            if (!b) {
                break;
            }
        }
        System.out.println("全部出队");
        while (true) {
            String dequeue = circleArrayQueue.dequeue();
            if (dequeue == null) {
                break;
            }
            System.out.println(dequeue);
        }
    }
}
