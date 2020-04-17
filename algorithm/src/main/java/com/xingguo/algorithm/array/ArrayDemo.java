/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.array;

/**
 * ArrayDemo
 * 数组
 *
 * @author guoxing
 * @date 2020/4/16 3:23 PM
 * @since
 */
public class ArrayDemo {
    public static void main(String[] args) {
        int[] intArray = new int[]{4, 2, 3, 5, 9, 6};
        // 预期结果为 -1
        System.out.println(sentryFind(intArray, 1));
        // 预期结果为 4
        System.out.println(sentryFind(intArray, 9));
    }

    public static void test() {
        // 二维数组
        int[][] array = new int[][]{{1, 2}, {2, 3}, {3, 5}};
        //二维数组的 寻址公式 为 ; 二维数组 int[n][m] ,查找 int[i][j] (i<n,j<m) ; 寻址公式为 : targetAddress = baseAddress(起始地址) + (i*m+j)*单个元素占用空间大小
        System.out.println(array[1][0] == 2);
    }


    /**
     * 利用哨兵实现查找指定元素在数组中的索引位置
     * 使用哨兵保证了数组查询不会越界
     *
     * @param array 数组
     * @param value 元素值
     */
    public static int sentryFind(int[] array, int value) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int length = array.length;
        // 由于需要利用数组中最后一个元素作为哨兵使用,所以先对最后一个元素做判断
        if (array[length - 1] == value) {
            return length - 1;
        }
        int temp = array[length - 1];
        array[length - 1] = value;

        int sentry = 0;
        // 遍历数组,获取值相等的数组中索引位置
        while (sentry < length) {
            if (array[sentry] == value) {
                break;
            }
            ++sentry;
        }
        // 将初始数组中的最后一个值重新保存到原有数组索引所在位置
        array[length - 1] = temp;
        // 查找到的元素所在位置是哨兵所在位置,则说明数组中不存在该元素
        if (sentry == length - 1) {
            return -1;
        } else {
            // 元素在数组中的索引位置
            return sentry;
        }
    }
}
