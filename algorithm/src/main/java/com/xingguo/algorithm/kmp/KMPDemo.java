/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.kmp;

/**
 * KMPDemo
 * 字符串匹配 kmp 算法
 * TODO 使用动态规划来处理next数组
 *
 * @author guoxing
 * @date 2020/6/5 9:41 PM
 * @since
 */
public class KMPDemo {

    /**
     * @param main  主串
     * @param model 模式串
     * @return 模式串和主串可以匹配的起始字符在主串中对应的索引位置
     */
    public int kmp(char[] main, char[] model) {
        if (main == null || model == null || main.length == 0 || model.length == 0) {
            return -1;
        }
        int mainLength = main.length;
        int modelLength = model.length;
        // 获取模式串的 next数组
        int[] next = getNext(model, modelLength);
        // 主串和模式串相匹配模式串的匹配长度,或称为模式串中坏字符的索引值/模式串移动后起始对比的字符索引值
        int j = 0;
        // 遍历主串
        for (int i = 0; i < mainLength; ++i) {
            // 当j大于0意味着当前位置模式串和主串存在相匹配的字符,则继续对比字符,如果出现不能匹配的字符,则根据KMP的next数组进行移动模式串
            while (j > 0 && main[i] != model[j]) { // 一直找到main[i]和model[j]
                // 查找当前模式串的好前缀长度对应的next数组中最大移动长度,并重置当前模式串和主串匹配的最大长度
                // 实际是为了将模式串进行移动操作,这里j表示了模式串移动后重新开始进行字符匹配的索引位置,相当于将坏字符在模式串中的索引位置进行移动,
                j = next[j - 1] + 1;
            }
            // 继续对比模式串和主串
            if (main[i] == model[j]) {
                // 如果存在匹配的字符则模式串可以匹配的主串的长度+1
                ++j;
            }
            if (j == modelLength) { // 找到匹配模式串的了
                return i - modelLength + 1;
            }
        }
        return -1;
    }

    // b表示模式串，m表示模式串的长度
    private int[] getNext(char[] b, int m) {
        /**
         * next数组中存储的值代表了当模式串和主串进行匹配时当出现坏字符时,模式串移动后要重新匹配的开始下标
         */
        // 初始化next数组
        int[] next = new int[m];
        // 模式串的第一个字符,肯定不存在最长可匹配前缀子串
        next[0] = -1;
        // next数组中存储的当前模式串前缀子串最长可匹配前缀子串的最后一个字符的下标值
        int k = -1;
        // 遍历模式串的所有前缀子串
        for (int i = 1; i < m; ++i) {
            // k!=-1 意味着 b[0,i-1] 存在最长前缀子串 b[0,k],因此需要对比b[i]和b[k+1]是否相等,如果不相等则代表需要从b[0,next[k]]区间查找最长前缀子串,而b[0,next[k]]是属于b[0,i]的前缀子串
            while (k != -1 && b[k + 1] != b[i]) {
                // k != -1 , b[k+1] != b[i] 时代表 当前b[0,i-1] 中包含最长前缀子串 b[0,k],当模式串的前缀子串[0,i-1]增加一位变为b[0,i]时,由于b[k+1] != b[i]
                // 因此需要在 b[0,next[k]]区间(次优最长前缀子串)开始进行模式串和主串的对比
                k = next[k];
            }
            // 从模式串的k位置索引开始进行字符匹配
            if (b[k + 1] == b[i]) {
                // 当k+1索引位置和 i相等时,则代表 b[0,k+1] 为 b[0,i]的最长前缀子串
                ++k;
            }
            // 修改next数组中存储的值
            next[i] = k;
        }
        return next;
    }

    public static void main(String[] args) {
        char[] modelCharArray = "ababacd".toCharArray();
        char[] mainCharArray = "ababaeabac".toCharArray();
        KMPDemo kmpDemo = new KMPDemo();
        int kmp = kmpDemo.kmp(mainCharArray, modelCharArray);
        assert kmp == -1;
    }
}
