/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.array;

import java.util.Random;

/**
 * DivideAndConquerDemo
 * 分治算法
 *
 * @author guoxing
 * @date 2020/5/14 3:12 PM
 * @since
 */
public class DivideAndConquerDemo {

    static class Demo {
        public void sortArray() {
            int[] array = new int[10];
            for (int i = 0; i < 10; i++) {
                int value = new Random().nextInt(100) + 1;
                array[i] = value;
                System.out.print(value + "  ");
            }
            System.out.println();
            System.out.println("======================");
            int count = count(array, array.length);
            for (int i = 0; i < 10; i++) {
                System.out.print(array[i] + "  ");
            }
            System.out.println();
            System.out.println(count);
        }

        private int num = 0; // 全局变量或者成员变量

        public int count(int[] a, int n) {
            num = 0;
            mergeSortCounting(a, 0, n - 1);
            return num;
        }

        private void mergeSortCounting(int[] a, int p, int r) {
            if (p >= r) return;
            int q = (p + r) / 2;
            mergeSortCounting(a, p, q);
            mergeSortCounting(a, q + 1, r);
            merge(a, p, q, r);
        }

        private void merge(int[] a, int p, int q, int r) {
            int i = p, j = q + 1, k = 0;
            int[] tmp = new int[r - p + 1];
            while (i <= q && j <= r) {
                if (a[i] <= a[j]) {
                    tmp[k++] = a[i++];
                } else {
                    num += (q - i + 1); // 统计p-q之间，比a[j]大的元素个数
                    tmp[k++] = a[j++];
                }
            }
            while (i <= q) { // 处理剩下的
                tmp[k++] = a[i++];
            }
            while (j <= r) { // 处理剩下的
                tmp[k++] = a[j++];
            }
            for (i = 0; i <= r - p; ++i) { // 从tmp拷贝回a
                a[p + i] = tmp[i];
            }
        }
    }

    public static void main(String[] args) {
//        new Demo().sortArray();
        sortArray();
    }

    static int n = 0;

    /**
     * 采用分治算法思想实现数组排序以及统计数组的逆序度
     */
    public static void sortArray() {
        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            int value = new Random().nextInt(100) + 1;
            array[i] = value;
            System.out.print(value + "  ");
        }
        System.out.println();
        System.out.println("======================");
        int count = mergeSortCount(array, 0, array.length - 1);
        for (int i = 0; i < 10; i++) {
            System.out.print(array[i] + "  ");
        }
        System.out.println();
        System.out.println(count);
    }

    public static int mergeSortCount(int[] array, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return 0;
        }

        // 获取中位索引值
        int midIndex = startIndex + (endIndex - startIndex) / 2;
        // 分解数组
        n += mergeSortCount(array, startIndex, midIndex);
        n += mergeSortCount(array, midIndex + 1, endIndex);
        // 合并小数组
        return merge(array, startIndex, midIndex, endIndex);
    }

    // 升序
    private static int merge(int[] array, int startIndex, int midIndex, int endIndex) {
        // 对两个小数组进行合并排序
        // 第一个数组的开始索引以及第二个数组的开始索引
        int firstStart = startIndex, secondStart = midIndex + 1;
        // 存储排序好的数据数组
        int[] temp = new int[endIndex - startIndex + 1];
        int i = 0;
        int n = 0;
        while (firstStart <= midIndex && secondStart <= endIndex) {
            if (array[firstStart] <= array[secondStart]) {
                temp[i++] = array[firstStart++];
            } else {
                // 逆序
                temp[i++] = array[secondStart++];
                n += (midIndex - firstStart + 1);
            }
        }
        while (firstStart <= midIndex) {
            temp[i++] = array[firstStart++];
        }
        while (secondStart <= endIndex) {
            temp[i++] = array[secondStart++];
        }

        if (temp.length >= 0) System.arraycopy(temp, 0, array, startIndex, temp.length);
        return n;
    }

}
