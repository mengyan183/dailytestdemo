/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.sort;

/**
 * MergeSort
 * 归并排序算法
 * 分治思想 + 递归编程技巧
 * TODO  具体实现尚未编写
 * @author guoxing
 * @date 2020/4/22 7:19 PM
 * @since
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] ints = {1, 2, 3, 4, 5, 6, 6, 232, 62};
        mergeSort(ints, 0, ints.length - 1);
    }

    public static void mergeSort(int[] array, int p, int r) {
        if (p >= r) {
            return;
        }
        int q = p + (r - p) / 2;
        mergeSort(array, p, q);
        mergeSort(array, q + 1, r);
        // 将数组 array[p,q] array[q+1,r] 合并
        merge(array, p, q, r);
    }

    private static void merge(int[] array, int startIndex, int middle, int endIndex) {

    }

}
