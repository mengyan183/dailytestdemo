/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.array;

import java.util.Random;

/**
 * BacktrackingDemo
 * 回溯算法
 * //TODO 深度优先搜索、八皇后、0-1 背包问题、图的着色、旅行商问题、数独、全排列、正则表达式匹配
 *
 * @author guoxing
 * @date 2020/5/14 4:41 PM
 * @since
 */
public class BacktrackingDemo {
    public static void main(String[] args) {
//        new BacktrackingDemo().cal8queens(0);
        new BacktrackingDemo().yesOrNoPackage();
    }


    /**
     * 0-1背包问题
     */
    public void yesOrNoPackage() {
        int length = 10;
        // 10个物品
        int[] weights = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            weights[i] = random.nextInt(100) + 1;
        }
        int packageLimitWeight = 100;
        int[] possible = new int[length];
        // 备忘录
        boolean[][] memory = new boolean[length][packageLimitWeight + 1];
        pushIntoPackage(0, 0, weights, length, packageLimitWeight, possible, memory);
    }

    /**
     * @param index              数组索引
     * @param totalWeight        当前背包内总重量
     * @param items              物品数组
     * @param length             数组长度
     * @param packageLimitWeight 背包限制重量
     * @param possible
     * @param memory
     */
    public void pushIntoPackage(int index, int totalWeight, int[] items, int length, int packageLimitWeight, int[] possible, boolean[][] memory) {
        // 背包内重量等于 背包限制重量 或 索引
        if (totalWeight == packageLimitWeight || index == length) {
            // 返回当前背包内的总重量
            System.out.println(totalWeight);
            System.out.printf("[");
            for (int i = 0; i < length; i++) {
                System.out.printf(possible[i] + (i == length - 1 ? "" : ","));
            }
            System.out.printf("]");
            System.out.println();
            return;
        }
        /**
         * 我们可以把物品依次排列，整个问题就分解为了 n 个阶段，每个阶段对应一个物品怎么选择。
         * 先对第一个物品进行处理，选择装进去或者不装进去，然后再递归地处理剩下的物品
         */
        // 不放置物品, 当全部的物品都选择不放置时
        possible[index] = 0;
        // 避免重复计算
        if (memory[index][totalWeight]) {
            return;
        }
        memory[index][totalWeight] = true;
        pushIntoPackage(index + 1, totalWeight, items, length, packageLimitWeight, possible, memory);
        // 递归回溯 当index选择不放置时,其他的索引所有可能放置条件
        if (totalWeight + items[index] <= packageLimitWeight) {// 在背包限制范围内放置物品
            possible[index] = items[index];
            pushIntoPackage(index + 1, totalWeight + items[index], items, length, packageLimitWeight, possible, memory);
        }
    }


    /**
     * 8皇后问题实际就是在8*8的二维数组中满足 垂直/水平/对角线上不会存在重复的元素
     * TODO
     */
    public void cal8queensV2() {
        // 棋盘
        int[][] board = new int[8][8];
        // 每次从棋盘的第0行开始
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                // 判断 在当前行的当前列放置是否合适

                board[column][row] = 1;
            }
        }

    }


    int[] result = new int[8];//全局或成员变量,下标表示行,值表示queen存储在哪一列

    public void cal8queens(int row) { // 调用方式：cal8queens(0);
        if (row == 8) { // 8个棋子都放置好了，打印结果
            printQueens(result);
            return; // 8行棋子都放好了，已经没法再往下递归了，所以就return
        }
        for (int column = 0; column < 8; ++column) { // 每一行都有8中放法
            if (isOk(row, column)) { // 有些放法不满足要求
                result[row] = column; // 第row行的棋子放到了column列
                cal8queens(row + 1); // 考察下一行
            }
        }
    }

    private boolean isOk(int row, int column) {//判断row行column列放置是否合适
        int leftup = column - 1, rightup = column + 1;
        for (int i = row - 1; i >= 0; --i) { // 逐行往上考察每一行
            if (result[i] == column) return false; // 第i行的column列有棋子吗？
            if (leftup >= 0) { // 考察左上对角线：第i行leftup列有棋子吗？
                if (result[i] == leftup) return false;
            }
            if (rightup < 8) { // 考察右上对角线：第i行rightup列有棋子吗？
                if (result[i] == rightup) return false;
            }
            --leftup;
            ++rightup;
        }
        return true;
    }

    private void printQueens(int[] result) { // 打印出一个二维矩阵
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                if (result[row] == column) System.out.print("Q ");
                else System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
