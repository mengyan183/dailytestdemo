/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.stack;

/**
 * ArrayStack
 * 使用数组实现一个栈
 * <p>
 * 栈的特点为 先进后出,后进先出
 *
 * @author guoxing
 * @date 2020/4/18 2:50 PM
 * @since
 */
public class ArrayStack {
    // 使用数组存储栈中元素
    private Object[] objects;
    // 当前栈中已存在元素数量
    private int count;
    // 栈的容量
    private final int n;

    /**
     * 创建栈
     *
     * @param n
     */
    public ArrayStack(int n) {
        this.n = n;
        this.objects = new Object[n];
    }

    /**
     * 入栈
     */
    public Object in(Object o) {
        if (count == n) {
            return null;
        }
        // 将数据加入到数组中
        objects[count++] = o;
        return o;
    }

    /**
     * 出栈
     *
     * @return
     */
    public Object pop() {
        if (count == 0) {
            return null;
        }
        return objects[--count];
    }


}
