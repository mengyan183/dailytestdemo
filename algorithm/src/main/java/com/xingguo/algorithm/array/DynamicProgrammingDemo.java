/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.array;

import sun.jvm.hotspot.utilities.Assert;

/**
 * DynamicProgrammingDemo
 * 动态规划
 * <p>
 * //TODO : 淘宝的“双十一”购物节有各种促销活动，比如“满 200 元减 50 元”。假设购物车中有 n 个（n>100）想买的商品，希望从里面选几个，在凑够满减条件的前提下，让选出来的商品价格总和最大程度地接近满减条件（200 元）
 * // TODO : https://time.geekbang.org/column/article/74788 杨辉三角问题
 * //TODO : 硬币问题 :假设我们有几种不同币值的硬币 v1，v2，……，vn（单位是元）。如果我们要支付 w 元，求最少需要多少个硬币。比如，我们有 3 种不同的硬币，1 元、3 元、5 元，我们要支付 9 元，最少需要 3 个硬币（3 个 3 元的硬币）
 *
 * @author guoxing
 * @date 2020/5/15 1:40 PM
 * @since
 */
public class DynamicProgrammingDemo {

    public static void main(String[] args) {
//        ZeroAndOnePackage zeroAndOnePackage = new ZeroAndOnePackage();
////        zeroAndOnePackage.backTracking(0, 0);
////        zeroAndOnePackage.backTrackingV2(0, 0);
//        long l = System.currentTimeMillis();
//        int i = zeroAndOnePackage.dynamicProgramming();
//        System.out.println("塞入物品的最大总重量为" + i + "时间为: " + (System.currentTimeMillis() - l));
//        l = System.currentTimeMillis();
//        int i1 = zeroAndOnePackage.dynamicProgrammingV2();
//        System.out.println("塞入物品的最大总重量为" + i + "时间为: " + (System.currentTimeMillis() - l));

//        ZeroAndOnePackageWithValue zeroAndOnePackageWithValue = new ZeroAndOnePackageWithValue();
//        zeroAndOnePackageWithValue.backTracking(0, 0, 0);
//        int maxValue = zeroAndOnePackageWithValue.dynamicProgramming();
//        System.out.println("在不超过背包容量时塞入物品的最大价值为:" + maxValue);


//        int minDist = new Matrix().minDist();
//        System.out.println("最短路径为" + minDist);
//        int minDistDP = new Matrix().minDistDP();
//        Assert.that(minDist == minDistDP, "答案不同,minDist方法有误");
        int minDist = new Matrix().minDist(1,2);
        System.out.println("最短路径为" + minDist);

    }

    /**
     * 棋盘最短路径
     */
    static class Matrix {
        // 一个4*4二维数组 每个位置的路径长度
        private int[][] pathArray = new int[][]{{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
        // 矩阵的长度 二维数组
        private int length = 4;
        // 矩阵的宽度 一维数组
        private int width = 4;

        /**
         * 指定棋盘位置 [0][0]到 [customI-1][customJ-1] 的最短路径
         *
         * @param customI 长度
         * @param customJ 宽度
         * @return
         */
        public int minDist(int customI, int customJ) {
            if (!(customI > 0 && customI <= length)) {
                customI = length;
            }
            if (!(customJ > 0 && customJ <= width)) {
                customJ = width;
            }
            int[][] states = new int[customJ][customI];
            states[0][0] = pathArray[0][0];
            // 初始化第一行状态
            for (int i = 1; i < customI; i++) {
                states[0][i] = states[0][i - 1] + pathArray[0][i];
            }
            // 初始化第一列状态
            for (int j = 1; j < customJ; j++) {
                states[j][0] = states[j - 1][0] + pathArray[j][0];
            }
            for (int i = 1; i < customI; i++) {
                for (int j = 1; j < customJ; j++) {
                    states[i][j] = Math.min(states[i - 1][j], states[i][j - 1]) + pathArray[i][j];
                }
            }
            return states[customJ - 1][customI - 1];
        }

        // 获取 从[0][0] 到 [length][width]的最短路径
        public int minDist() {
            return minDist(length, width);
        }

        public int minDistDP() {
            int n = 4;
            int[][] matrix = pathArray;
            int[][] states = new int[n][n];
            int sum = 0;
            for (int j = 0; j < n; ++j) { // 初始化states的第一行数据
                sum += matrix[0][j];
                states[0][j] = sum;
            }
            sum = 0;
            for (int i = 0; i < n; ++i) { // 初始化states的第一列数据
                sum += matrix[i][0];
                states[i][0] = sum;
            }
            for (int i = 1; i < n; ++i) {
                for (int j = 1; j < n; ++j) {
                    states[i][j] =
                            matrix[i][j] + Math.min(states[i][j - 1], states[i - 1][j]);
                }
            }
            return states[n - 1][n - 1];
        }
    }


    /**
     * 0-1背包问题 升级版 ,商品存在价值
     */
    static class ZeroAndOnePackageWithValue {
        // 回溯算法实现。注意：我把输入的变量都定义成了成员变量。
        private int maxW = Integer.MIN_VALUE; // 最大重量 结果放到maxW中
        private int maxV = Integer.MIN_VALUE; // 最大价值 结果放到maxW中
        private int[] weight = {2, 2, 4, 6, 3};  // 物品重量
        private int[] value = {9, 6, 4, 6, 3};  // 物品价值
        private int n = 5; // 物品个数
        private int w = 9; // 背包承受的最大重量


        /**
         * 回溯算法
         *
         * @param index       将要操作的物品序号
         * @param totalWeight 当前背包内已塞入物品的总重量
         * @param totalValue  当前背包内已塞入物品的总价值
         */
        public void backTracking(int index, int totalWeight, int totalValue) {
            // 当以塞入全部的物品或塞入物品的重量已达到背包上限时,则代表已塞入结束
            if (index == n || totalWeight == w) {
                System.out.println("当前塞入物品的总重量为" + totalWeight + ";总价值为" + totalValue);
                return;
            }
            // 不塞入 index 序号的物品
            backTracking(index + 1, totalWeight, totalValue);
            // 判断塞入index 序号物品是否超过背包的容量
            if (totalWeight + weight[index] <= w) {
                backTracking(index + 1, totalWeight + weight[index], totalValue + value[index]);
            }
        }

        /**
         * 获取背包中的最大价值,且限定背包最大容量
         *
         * @return 背包中的最大价值
         */
        public int dynamicProgramming() {
            // 存储不同物品重量的最大价值
            int[][] values = new int[n][w + 1];
            // 选择第一个物品不放入背包
            values[0][0] = 0;  // 第一行的数据要特殊处理，可以利用哨兵优化
            // 选择将一个物品放入到背包中
            if (weight[0] <= w) {
                values[0][weight[0]] = value[0];
            }
            for (int index = 1; index < n; index++) {
                for (int j = 0; j <= w; ++j) {// 不把第index个物品放入背包
                    // 遍历第i-1层的决策,由于选择不把索引为 index 的物品放入到背包中,则背包内塞入物品的重量没有改变,因此将上一层的状态等于当前层的状态
                    if (values[index - 1][j] > 0) values[index][j] = values[index - 1][j];
                }
                for (int j = 0; j <= w - weight[index]; j++) {
                    // 放入索引为index 的物品
                    // 放入物品后物品总价值
                    int v = values[index - 1][j] + value[index];
                    // 对比当前相同物品个数,相同重量的总价值
                    if (v > values[index][j + weight[index]]) {
                        // 塞入物品的价值超过相同状态不同决策的价值,则保存价值更大的值
                        values[index][j + weight[index]] = v;
                    }
                }
            }
            // 找出最大值
            int maxvalue = -1;
            for (int j = 0; j <= w; ++j) {
                if (values[n - 1][j] > maxvalue) {
                    maxvalue = values[n - 1][j];
                }
            }
            return maxvalue;
        }
    }

    /**
     * 0-1背包问题
     */
    static class ZeroAndOnePackage {
        // 回溯算法实现。注意：我把输入的变量都定义成了成员变量。
        private int maxW = Integer.MIN_VALUE; // 结果放到maxW中
        private int[] weight = {2, 2, 4, 6, 3};  // 物品重量
        private int n = 5; // 物品个数
        private int w = 9; // 背包承受的最大重量
        private boolean[][] memo = new boolean[n][w + 1]; // 备忘录

        /**
         * 回溯算法
         *
         * @param index       将要操作的物品序号
         * @param totalWeight 当前背包内已塞入物品的总重量
         */
        public void backTracking(int index, int totalWeight) {
            // 当以塞入全部的物品或塞入物品的重量已达到背包上限时,则代表已塞入结束
            if (index == n || totalWeight == w) {
                if (totalWeight > maxW) {
                    maxW = totalWeight;
                    System.out.println("当前塞入物品的总重量为" + maxW);
                }
                return;
            }
            // 不塞入 index 序号的物品
            backTracking(index + 1, totalWeight);
            // 判断塞入index 序号物品是否超过背包的容量
            if (totalWeight + weight[index] <= w) {
                backTracking(index + 1, totalWeight + weight[index]);
            }
        }

        /**
         * 回溯算法
         * 增加使用备忘录方式,减少冗余计算
         *
         * @param index       将要操作的物品序号
         * @param totalWeight 当前背包内已塞入物品的总重量
         */
        public void backTrackingV2(int index, int totalWeight) {
            // 当以塞入全部的物品或塞入物品的重量已达到背包上限时,则代表已塞入结束
            if (index == n || totalWeight == w) {
                if (totalWeight > maxW) {
                    maxW = totalWeight;
                    System.out.println("当前塞入物品的总重量为" + maxW);
                }
                return;
            }
            // 判断当前决策是否已执行
            if (memo[index][totalWeight]) return;
            memo[index][totalWeight] = true;
            // 不塞入 index 序号的物品
            backTrackingV2(index + 1, totalWeight);
            // 判断塞入index 序号物品是否超过背包的容量
            if (totalWeight + weight[index] <= w) {
                // 塞入 index 序号的物品
                backTrackingV2(index + 1, totalWeight + weight[index]);
            }
        }

        /**
         * 动态规划
         * 动态规划实际就是通过上一次的决策结果 来推进下一次的决策结果,上一次不同的决策结果影响着下一次不同的决策结果
         */
        public int dynamicProgramming() {
            // 生成一个由商品数量和背包总重量组成的二维数组
            boolean[][] states = new boolean[n][w + 1]; // 默认值false
            // 选择第一个物品不放入背包
            states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
            // 选择将一个物品放入到背包中
            if (weight[0] <= w) {
                states[0][weight[0]] = true;
            }
            // 从第二个物品开始进行决策
            for (int i = 1; i < n; ++i) { // 动态规划状态转移
                for (int j = 0; j <= w; ++j) {// 不把第i个物品放入背包
                    // 遍历第i-1层的决策,由于选择不把索引为 i 的物品放入到背包中,则背包内塞入物品的重量没有改变,因此将上一层的状态等于当前层的状态
                    if (states[i - 1][j]) states[i][j] = states[i - 1][j];
                }
                //选择塞入第i个物品, 限制塞入第i个物品后,塞入后的物品总重量需要小于等于背包总重量
                for (int j = 0; j <= w - weight[i]; ++j) {//把第i个物品放入背包
                    // 需要上一层的执行过该决策才能在下次层执行该决策
                    if (states[i - 1][j]) states[i][j + weight[i]] = true;
                }
            }
            // 获取最后一层执行决策对应的重量,为当前背包可以塞入物品的最大重量
            for (int i = w; i >= 0; --i) { // 输出结果
                if (states[n - 1][i]) return i;
            }
            return 0;
        }

        /**
         * 动态规划 第二个版本使用一维数组
         *
         * @return 背包内所有塞入物品的最大总重量
         */
        public int dynamicProgrammingV2() {
            // 使用背包最大限定容量生成一个一维数组
            boolean[] states = new boolean[w + 1];
            // 所有决策都不塞入物品
            states[0] = true;
            // 对第i个物品进行决策
            for (int i = 1; i < n; i++) {
                // 如果要塞入 第i个物品,限定当前已存在的最大重量为 限定重量-当前要塞入物品的重量
                // 重量从大往小进行遍历,避免重复计算
                for (int maxW = w - weight[i]; maxW >= 0; maxW--) {
                    if (states[maxW]) {
                        //获取到当前已塞入物品的最大总重量,塞入当前物品,设置最大总重量
                        states[maxW + weight[i]] = true;
                        break;
                    }
                }
            }

            // 获取背包内最大总重量
            for (int maxW = w; maxW >= 0; maxW--) {
                if (states[maxW]) {
                    return maxW;
                }
            }
            return 0;
        }


        public int getMaxW() {
            return this.maxW;
        }
    }
}
