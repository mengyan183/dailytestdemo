/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.stack;

import org.w3c.dom.Node;

/**
 * LinkedStack
 * 使用单链表实现栈操作特性
 *
 * @author guoxing
 * @date 2020/4/18 6:27 PM
 * @since
 */
public class LinkedStack<V> {

    // 单链表节点
    class Node<V> {
        private final int hash;
        private final V v;
        private Node<V> next;

        Node(V v, Node<V> next) {
            this.hash = v.hashCode();
            this.v = v;
            this.next = next;
        }

        public V getV() {
            return v;
        }
    }

    // 头节点
    private Node<V> head;

    public LinkedStack() {

    }

    /**
     * 入栈
     */
    public void in(V v) {
        if (head == null) {
            head = new Node<V>(v, null);
        } else {
            head = new Node<V>(v, head);
        }
    }

    /**
     * 出栈
     *
     * @return
     */
    public V pop() {
        if (head == null) {
            return null;
        }
        Node<V> next = head.next;
        if (next == null) {
            V v = head.getV();
            head = null;
            return v;
        } else {
            Node<V> temp = head;
            temp.next = null;
            head = next;
            return temp.getV();
        }
    }
}
