/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.queue;

import org.w3c.dom.Node;

/**
 * LinkedQueue
 * 使用链表实现队列
 *
 * @author guoxing
 * @date 2020/4/19 4:03 PM
 * @since
 */
public class LinkedQueue<V> {
    class Node<V> {
        private V v;
        private Node<V> next;

        Node(V v, Node<V> next) {
            this.v = v;
            this.next = next;
        }
    }

    private Node<V> head;
    private Node<V> tail;

    public LinkedQueue() {

    }

    /**
     * 入队操作 尾插法
     *
     * @param v
     * @return
     */
    public boolean enqueue(V v) {
        Node<V> node = new Node<V>(v, null);
        if (head == null) {
            head = node;
            tail = head;
            return true;
        }
        tail.next = node;
        tail = node;
        return true;
    }

    /**
     * 出队
     *
     * @return
     */
    public V dequeue() {
        if (head == null) {
            // 元素已全部出队
            return null;
        }
        Node<V> temp = head;
        V v = temp.v;
        head = head.next;
        // 将原头节点的next指针指向空
        temp.next = null;
        // help gc
        temp = null;
        return v;
    }
}
