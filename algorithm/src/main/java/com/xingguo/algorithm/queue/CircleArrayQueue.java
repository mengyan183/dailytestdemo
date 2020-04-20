/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.queue;

/**
 * CircleArrayQueue
 * 使用数组实现循环队列
 *
 * @author guoxing
 * @date 2020/4/19 3:22 PM
 * @since
 */
public class CircleArrayQueue {
    private String[] kArray;
    // 容量
    private int n;

    // head
    private int head;

    // tail , 当队列未满时,tail 指向数组中下一个元素为空的索引;反之 则 tail = head 且 tail的下一个索引位置元素不为空;
    private int tail;

    public CircleArrayQueue(int n) {
        this.kArray = new String[n];
        this.head = 0;
        this.tail = 0;
        this.n = n;
    }

    /**
     * 入队
     *
     * @param value
     * @return
     */
    public boolean enQueue(String value) {
        // 队列已满
        if (head == tail && kArray[(head + 1) % n] != null) {
            return false;
        }
        kArray[tail] = value;
        tail = (tail + 1) % n;
        return true;
    }

    /**
     * 出队
     */
    public String dequeue() {
        if (head == tail && kArray[head] == null) {
            // 队列已空
            return null;
        }
        String s = kArray[head];
        kArray[head] = null;
        head = (head + 1) % n;
        return s;
    }
}
