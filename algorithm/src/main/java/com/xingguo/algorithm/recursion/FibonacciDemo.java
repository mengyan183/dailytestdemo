/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.recursion;

/**
 * FibonacciDemo
 * 斐波那契数列
 * 1+1 + (1+1) + (1+(1+1)) + ((1+1)+(1+(1+1)))
 *
 * @author guoxing
 * @date 2020/7/1 10:46 AM
 * @since
 */
public class FibonacciDemo {

    /**
     * 执行过程类似于双指针移动
     * F(1)=1，F(2)=1, F(n)=F(n - 1)+F(n - 2)
     * total = F(1) + ... + F(n)
     *
     * @param args
     */
    public static void main(String[] args) {
        int first = 1;
        int second = 1;
        int total = first + second;
        System.out.printf(" " + first + " " + second);
        for (int i = 0; i < 5; i++) {
            int temp = second;
            second = first + second;
            first = temp;
            System.out.printf(" " + second);
            total += second;
        }
        System.out.println("=============");
        System.out.println(total);
    }
}
