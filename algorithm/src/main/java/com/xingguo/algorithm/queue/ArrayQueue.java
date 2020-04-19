/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.queue;

/**
 * ArrayQueue
 * 使用数组实现队列
 *
 * @author guoxing
 * @date 2020/4/19 3:22 PM
 * @since
 */
public class ArrayQueue {
    private String[] kArray;
    // 容量
    private int n;

    // head
    private int head = -1;

    // tail , 当队列未满时,tail 指向数组中下一个元素为空的索引;反之 则 tail = n;
    private int tail;

    public ArrayQueue(int n) {
        this.kArray = new String[n];
        this.head = 0;
        this.tail = 0;
        this.n = n;
    }

    /**
     * 入队
     * 时间复杂度分析 : 只分析入队时, 最好时间复杂度 O(1),最差时间复杂度为O(n),均衡时间复杂度为O(1), (n-1)O(1)+O(n)= O(1)
     *
     * @param value
     * @return
     */
    public boolean enQueue(String value) {
        if (tail == n && head == 0) {
            // 队列无剩余空间
            return false;
        }
        if (tail == n) {
            // 已有出队元素,数组中存在不连续的空间 ,移除数组中已出队数据,移动数组中的数据
            for (int i = head; i < n; i++) {
                kArray[i - head] = kArray[i];
                kArray[i] = null;
                tail--;
            }
            head = 0;
        }
        kArray[tail++] = value;
        return true;
    }

    /**
     * 出队
     */
    public String dequeue() {
        if (head == tail) {
            return null;
        }
        return kArray[head++];
    }
}
