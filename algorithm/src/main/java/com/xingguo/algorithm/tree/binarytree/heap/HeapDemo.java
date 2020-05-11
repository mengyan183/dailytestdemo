/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.tree.binarytree.heap;

import lombok.AllArgsConstructor;

/**
 * HeapDemo
 * 堆相关demo
 * 堆是完全二叉树且要求节点的值大于等于/小于等于 子节点的值
 *
 * @author guoxing
 * @date 2020/5/4 5:18 PM
 * @since
 */
public class HeapDemo {

    public static void main(String[] args) {

//        commonHeap();
        int[] array = new int[11];
        for (int i = 1; i < array.length; i++) {
            array[i] = i;
        }
        buildBigHeapifyFromBot(array, array.length - 1);
        list(array);
        System.out.println("==========================");
        bigHeapAscSort(array, array.length - 1);
        list(array);
    }

    public static void commonHeap() {
        int size = 11;
        int[] bigHeap = new int[size];
        // 数组保存从下标1开始保存数据
        for (int i = 1; i < size; i++) {
            insert(bigHeap, i, i);
        }
        list(bigHeap);
        // 进行树化
        TreeNode root = treeify(bigHeap, 1);
        bigHeap = removeHeapTop(bigHeap);
        System.out.println("=======================");
        list(bigHeap);
    }

    public static void list(int[] array) {
        int size = array.length;
        for (int i = 1; i < size; i++) {
            System.out.println(array[i]);
        }
    }

    public static int[] removeHeapTop(int[] array) {
        if (array == null || array.length == 1) {
            return null;
        }
        array[1] = array[array.length - 1];
        int[] ints = new int[array.length - 1];
        int newArrayLength = ints.length;
        for (int i = 0; i < newArrayLength; i++) {
            ints[i] = array[i];
        }
        bigHeapifyFromTop(ints, 1);
        return ints;
    }

    /**
     * 大顶堆自上而下堆化操作
     *
     * @param ints
     * @param index
     */
    private static void bigHeapifyFromTop(int[] ints, int index) {
        int length = ints.length;
        if (length == index - 1) {
            return;
        }
        int leftNodeIndex = (int) Math.pow(2, index);
        int rightNodeIndex = (int) (Math.pow(2, index)) + 1;
        int maxIndex = index;
        if (leftNodeIndex >= length) {
            return;
        } else if (rightNodeIndex < length) {
            int currentValue = ints[index];
            int leftNodeValue = ints[leftNodeIndex];
            int rightNodeValue = ints[rightNodeIndex];
            int max;
            if (leftNodeValue <= rightNodeValue) {
                max = rightNodeValue;
                maxIndex = rightNodeIndex;
            } else {
                max = leftNodeValue;
                maxIndex = leftNodeIndex;
            }
            if (currentValue < max) {
                ints[index] = max;
                ints[maxIndex] = currentValue;
                bigHeapifyFromTop(ints, maxIndex);
            } else {
                return;
            }
        } else {
            int currentValue = ints[index];
            int leftNodeValue = ints[leftNodeIndex];
            if (currentValue < leftNodeValue) {
                ints[index] = leftNodeValue;
                ints[maxIndex] = currentValue;
                bigHeapifyFromTop(ints, maxIndex);
            } else {
                return;
            }
        }
    }

    /**
     * 将数组进行树化进行操作,构建完全二叉树
     *
     * @param array
     */
    public static TreeNode treeify(int[] array, int index) {
        if (array == null) {
            return null;
        }
        int length = array.length;
        if (length == 1) {
            return null;
        }
        if (index >= length) {
            return null;
        }
        // 获取2的幂次方
        int pow = (int) Math.pow(2, index);
        return new TreeNode(array[index], treeify(array, pow), treeify(array, pow + 1));

    }

    @AllArgsConstructor
    public static class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode right;
    }

    /**
     * 堆中插入数据
     *
     * @param array
     * @param index
     * @param value
     */
    public static void insert(int[] array, int index, int value) {
        array[index] = value;
        bigHeapify(array, index);
    }

    /**
     * 大顶堆 堆化
     *
     * @param array
     * @param index
     */
    public static void bigHeapify(int[] array, int index) {
        if (index == 1) {
            return;
        }
        int value = array[index];
        int parentIndex = index / 2;
        // 获取父节点的值
        int parentValue = array[parentIndex];
        if (parentValue < value) {
            array[index] = parentValue;
            array[parentIndex] = value;
            bigHeapify(array, parentIndex);
        }
    }

    /**
     * 从最后一个非叶子节点开始进行堆化处理
     *
     * @param array
     * @param count
     */
    public static void buildBigHeapifyFromBot(int[] array, int count) {
        // 从最后一个非叶子节点开始遍历
        for (int i = count / 2; i >= 1; i--) {
            bigHeapifyFromBot(array, i, count);
        }
    }

    /**
     * 大顶堆自下而上堆化 , (通过节点和叶子节点值对比,判断是否满足堆的值条件)
     *
     * @param array
     * @param index
     * @param count 可用值的数量
     */
    public static void bigHeapifyFromBot(int[] array, int index, int count) {
        if (array == null || array.length <= 1 || count < 1) {
            return;
        }
        // 当前数组的长度,数组从索引为1开始存储数据, 因此 数组实际可以遍历的长度为count+1(索引为0的空节点)
        int length = count + 1;
        while (true) {
            if (length <= index) {
                break;
            }
            // 当前节点子树最大值的索引值
            int maxValueIndex = index;
            int currentValue = array[index];
            int maxValue = currentValue;
            int leftNodeIndex = 2 * index;
            int rightNodeIndex = leftNodeIndex + 1;
            // 对比左子树节点判断是否大于当前节点的值
            if (leftNodeIndex < length && array[leftNodeIndex] > maxValue) {
                maxValueIndex = leftNodeIndex;
                maxValue = array[leftNodeIndex];
            }
            // 对比右子树节点的值
            if (rightNodeIndex < length && array[rightNodeIndex] > maxValue) {
                maxValueIndex = rightNodeIndex;
                maxValue = array[rightNodeIndex];
            }
            // 左右子树都小于当前节点的值,不需要在进行遍历比较
            if (maxValueIndex == index) {
                break;
            }
            // 交换最大值所在索引和当前索引的值
            int temp = array[maxValueIndex];
            array[index] = temp;
            array[maxValueIndex] = currentValue;
            // 继续循环递归比较节点
            index = maxValueIndex;
        }
    }

    /**
     * 小顶堆 堆化
     *
     * @param array
     * @param index
     */
    public static void smallHeapify(int[] array, int index) {
        if (index == 1) {
            return;
        }
        int value = array[index];
        int parentIndex = index / 2;
        // 获取父节点的值
        int parentValue = array[parentIndex];
        if (parentValue > value) {
            array[index] = parentValue;
            array[parentIndex] = value;
            bigHeapify(array, parentIndex);
        }
    }

    /**
     * 大顶堆正序排序操作
     * 对大顶堆进行正序排序操作,和删除堆顶元素类似
     * 1:交换堆顶元素和最后叶子节点进行数据交换
     * 2:删除最后的叶子节点(只是让堆中存储的元素数量减少,并未将数据直接删除)
     * 3:对堆顶元素进行堆化操作,再继续从第一步开始循环直到堆中的元素只剩下一个节点终止循环,此时数组就是一个完全有序的数组了
     *
     * @param array
     * @param count
     */
    public static void bigHeapAscSort(int[] array, int count) {
        int k = count;
        while (k > 1) {
            // 交换堆顶元素和最后叶子节点的值
            int top = array[1];
            array[1] = array[k];
            array[k] = top;
            // 对交换后的堆顶节点进行堆化操作; --k 的操作相当于对于堆中交换后的叶子节点进行删除操作
            bigHeapifyFromBot(array, 1, --k);
        }
    }

}
