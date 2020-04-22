/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.sort;

/**
 * ChooseSort
 * 选择排序
 *
 * @author guoxing
 * @date 2020/4/21 8:53 PM
 * @since
 */
public class ChooseSort {

    public static void main(String[] args) {
        int[] array = {2141, 24, 412, 41241, 124211, 4363, 564564};
        int length = array.length;
        chooseSort(array, length);
        int i = 0;
        while (i < length) {
            System.out.println(array[i++]);
        }
    }

    /**
     * 选择排序
     * 获取无序区间中的最小元素,和无序区间的头部位置进行交换
     *
     * @param array
     * @param length
     */
    private static void chooseSort(int[] array, int length) {
        for (int i = 0; i < length; i++) {
            int min = array[i];
            int minIndex = i;
            for (int j = minIndex; j < length; j++) {//无序空间
                if (array[j] < min) {
                    min = array[j];
                    minIndex = j;
                }
            }
            array[minIndex] = array[i];//和无序空间的开始位置进行数据交换
            array[i] = min;
        }
    }
}
