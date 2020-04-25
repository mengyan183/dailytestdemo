/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.sort;

/**
 * CountingSort
 * 计数排序
 *
 * @author guoxing
 * @date 2020/4/25 6:28 PM
 * @since
 */
public class CountingSort {
    public static void main(String[] args) {
        int[] array = {2, 5, 3, 0, 2, 3, 0, 3, -1};
        // 获取最大的值,如果最小的值为负数,则需要将数组中的所有值都加上最小负数的绝对值
        Integer min = getMin(array);
        if (min == null) {
            return;
        }
        Integer max = getMax(array);
        if (max == null) {
            return;
        }
        // 设置桶的数量,当最小值小于0时桶的数量需要设置为 max-min,并设置遍历数组中每个值对应的数量
        int[] bucket = getCountBucket(array, min, max);

        // 遍历桶中的计数进行排序
        assert bucket != null;
        countSort(array, bucket, min);
        int i = 0;
        while (i < array.length) {
            System.out.println(array[i++]);
        }
    }

    private static void countSort(int[] array, int[] bucket, Integer min) {
        assert bucket != null;
        assert array != null;
        assert min != null;
        boolean flag = min < 0;
        int length = bucket.length;
        int lastIndex = 0;
        for (int i = 0; i < length; i++) {
            int count = bucket[i];
            if (count > 0) {
                for (int j = 0; j < count; j++) {
                    int value = i;
                    if (flag) {
                        value = i + min;
                    }
                    array[lastIndex++] = value;
                }
            }
        }
    }

    /**
     * 遍历数组并进行计数
     *
     * @param array
     * @param min
     * @param max
     * @return
     */
    private static int[] getCountBucket(int[] array, Integer min, Integer max) {
        assert min != null;
        assert max != null;
        assert array != null;
        boolean flag = min < 0;
        int bucketSize = max + (min < 0 ? (-(min - 1)) : 1);
        int[] bucket = new int[bucketSize];
        int length = array.length;
        if (length == 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            if (flag) {
                bucket[array[i] - min]++;
            } else {
                bucket[array[i]]++;
            }

        }
        return bucket;
    }

    /**
     * 获取数组中的最大值
     *
     * @param array
     * @return
     */
    private static Integer getMax(int[] array) {
        assert array != null;
        int length = array.length;
        if (length == 0) {
            return null;
        }
        int max = array[0];
        for (int i = 1; i < length; i++) {
            int value = array[i];
            if (max < value) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 获取数组中最小的值
     *
     * @param array
     * @return
     */
    private static Integer getMin(int[] array) {
        if (array == null) {
            return null;
        }
        int length = array.length;
        if (length == 0) {
            return null;
        }
        int min = array[0];
        for (int i = 1; i < length; i++) {
            int value = array[i];
            if (min > value) {
                min = value;
            }
        }
        return min;
    }
}
