/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.sort;

/**
 * InsertionSort
 * 插入排序
 *
 * @author guoxing
 * @date 2020/4/21 7:12 PM
 * @since
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] array = {2141, 24, 412, 41241, 124211, 4363, 564564};
        int length = array.length;
        insertionSort(array, length);
        int i = 0;
        while (i < length) {
            System.out.println(array[i++]);
        }
    }


    // 插入排序，a表示数组，n表示数组大小
    public static void insertionSort(int[] a, int n) {
        if (n <= 1) return;

        for (int i = 1; i < n; ++i) { // 无序区间,每循环一次,则有序区间长度加1,无序区间长度减1
            // 比较元素
            int value = a[i];
            int j = i - 1;
            //在有序数组[0,j]中 查找插入的位置;和当前比较元素之前的所有元素进行对比操作
            for (; j >= 0; --j) { // 这里代表的是有序区间
                // 首先和有序区间中的最后一个元素进行比较
                // 如果有序数组中的元素大于 要比较插入的元素,因此将元素往后移动一位
                if (a[j] > value) {
                    a[j + 1] = a[j];  // 数据移动
                } else {
                    break;
                }
            }
            a[j + 1] = value; // 插入数据
        }
    }

}
