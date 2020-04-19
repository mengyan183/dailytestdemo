/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.stack;

import com.xingguo.algorithm.queue.ArrayQueue;
import com.xingguo.algorithm.queue.LinkedQueue;
import org.junit.Test;

/**
 * LinkedQueueTest
 *
 * @author guoxing
 * @date 2020/4/19 3:41 PM
 * @since
 */
public class LinkedQueueTest {

    @Test
    public void linkedQueueTest() {
        LinkedQueue<String> linkedQueue = new LinkedQueue<String>();
        for (int i = 0; i < 10; i++) {
            boolean b = linkedQueue.enqueue(String.valueOf(i));
            if (!b) {
                break;
            }
        }
        System.out.println("出队开始");
        for (int i = 0; i < 5; i++) {
            System.out.println(linkedQueue.dequeue());
        }
        System.out.println("出队结束,再次入队");
        for (int i = 0; i < 5; i++) {
            boolean b = linkedQueue.enqueue(String.valueOf(i));
            if (!b) {
                break;
            }
        }
        System.out.println("全部出队");
        while (true) {
            String dequeue = linkedQueue.dequeue();
            if (dequeue == null) {
                break;
            }
            System.out.println(dequeue);
        }
    }
}
