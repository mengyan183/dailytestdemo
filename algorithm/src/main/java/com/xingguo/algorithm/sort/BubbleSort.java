/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.sort;

/**
 * BubbleSort
 * 冒泡排序
 *
 * @author guoxing
 * @date 2020/4/21 7:12 PM
 * @since
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {2141, 24, 412, 41241, 124211, 4363, 564564};
        bubbleSort(array);
        int i = 0;
        int length = array.length;
        while (i < length) {
            System.out.println(array[i++]);
        }
    }

    public static void bubbleSort(int[] valueArray) {
        int length = valueArray.length;
        int count = 0;
        int compareCount = 0;
        while (true) {
            boolean changeFlag = false;
            for (int i = 0; i < length - 1; i++) {
                compareCount++;
                if (valueArray[i] > valueArray[i + 1]) {
                    int temp = valueArray[i];
                    valueArray[i] = valueArray[i + 1];
                    valueArray[i + 1] = temp;
                    changeFlag = true;
                }
            }
            count++;
            if (!changeFlag) {
                break;
            }
        }
        System.out.println("冒泡次数为 : " + count);
        System.out.println("比较次数为 : " + compareCount);
    }

}
