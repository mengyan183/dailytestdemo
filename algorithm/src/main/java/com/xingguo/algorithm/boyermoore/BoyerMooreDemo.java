/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.boyermoore;

/**
 * BoyerMoore
 * 字符串匹配算法 BM
 *
 * @author guoxing
 * @date 2020/5/31 5:23 PM
 * @since
 */
public class BoyerMooreDemo {
    // 构建字符散列表
    public static final int SIZE = 256;

    /**
     * BM算法具体代码实现
     * 1: 根据坏字符规则获取到后移位数
     * 2: 由于单纯使用坏字符规则可能会出现模式串向前移动以及可能出现过度移动的情况,因此需要增加好后缀规则
     * 3: 好后缀相关规则:  在模式串中，查找跟好后缀匹配的另一个子串；  在好后缀的后缀子串中，查找最长的、能跟模式串前缀子串匹配的后缀子串；
     * 4: 为了提高好后缀的实现规则,需要预处理好后缀和最佳前缀数据
     * 5: 由于模式串的最佳后缀子串肯定属于模式串中的全部后缀子串的一种,因此可以先提前处理好模式串的全部后缀子串; 由于模式串的全部后缀子串的最后一个字符肯定都是相同的,因此只需要记录后缀子串的长度和模式串的最大索引值就可以确定一个模式串的后缀子串
     * 6: 使用suffix数组记录模式串截取掉后缀子串后,后缀子串在剩余子串中能够匹配的起始索引值,数组下标为后缀子串的长度
     *
     * TODO : 只使用好后缀规则实现BM算法
     *
     * @param mainString  主串
     * @param modelString 模式串
     * @return 最大移动位数
     */
    public int boyerMoore(char[] mainString, char[] modelString) {
        if (mainString == null || modelString == null) {
            return -1;
        }
        // 主串长度和模式串长度对比
        int mainLength = mainString.length;
        int modelLength = modelString.length;
        if (mainLength < modelLength) {
            return -1;
        }
        // 为了方便主串中的坏字符定位在模式串的索引位置 构建模式串中字符对应的最大索引值 hash表
        int[] modelCharHash = generateCharHash(modelString);
        // 存储后缀子串在 模式串截取掉后缀子串后剩余模式串中匹配的最大索引值, 数组下标为后缀子串的长度
        int[] suffixArray = new int[modelLength];
        // 存储当前模式串的后缀子串可以匹配的模式串的前缀子串
        boolean[] prefixArray = new boolean[modelLength];
        // 初始化模式串的后缀子串 前缀子串和后缀子串的初始化数据
        generateGoodSuffix(modelString, suffixArray, prefixArray);
        // 移动位置 ; mainStartIndex表示主串与模式串对齐的第一个字符,默认从主串索引为0位置开始
        int mainStartIndex = 0;
        // 模式串最大可以移动位数
        int maxMoveLength = mainLength - modelLength;
        // 使用坏字符规则计算移动位数
        while (mainStartIndex < maxMoveLength) {
            // 查找坏字符,从模式串的最后一个字符开始和主串相对应的索引位置的字符开始比较
            int bcModelIndex = modelLength - 1;

            for (; bcModelIndex >= 0; --bcModelIndex) {
                // 获取当前模式串 bcModelIndex索引位置对应主串索引位置(模式串和主串字符对比的起始索引位置mainStartIndex + 模式串的索引位置 bcModelIndex-1)的主串字符
                char mainC = mainString[mainStartIndex + bcModelIndex];
                // 遍历到的模式串的字符
                char modelC = modelString[bcModelIndex];
                if (mainC != modelC) {
                    //说明当前位置开始进行对比的模式串和主串出现不匹配的字符串,也就是所谓的坏字符 badCharacter
                    break;
                }
            }
            // bcModelIndex == -1 说明当前模式串和主串所在位置已匹配
            if (bcModelIndex < 0) {
                // 当前主串的起始位置开始和模式串能够完全匹配,不需要再移动模式串
                return mainStartIndex;
            }
            // 根据坏字符规则获取后移到的索引位置;
            char badCharacter = mainString[mainStartIndex + bcModelIndex];
            // 获取坏字符在模式串中的最大索引值
            int badCIndexInModel = modelCharHash[badCharacter];
            // 移动位数为 bcModelIndex - badCIndexInModel ; 这里暂不考虑往前移动的情况,也就是根据坏字符规则得到的移动位数
            int bcMoveLength = bcModelIndex - badCIndexInModel;
            // 获取好后缀规则获取的后移的位数
            int gsMoveLength = getByGs(bcModelIndex, modelLength, suffixArray, prefixArray);
            mainStartIndex += Math.max(bcMoveLength, gsMoveLength);
        }

        return -1;
    }

    /**
     * 获取好后缀规则中的移动位数
     *
     * @param bcModelIndex 坏字符在模式串中的索引
     * @param modelLength  模式串的长度
     * @param suffixArray  好后缀 数组
     * @param prefixArray  好后缀是否存在最佳前缀 数组
     * @return 移动位数
     */
    private int getByGs(int bcModelIndex, int modelLength, int[] suffixArray, boolean[] prefixArray) {
        // 计算好后缀的长度
        int goodSuffixLength = modelLength - 1 - bcModelIndex;
        // 从suffix中获取好后缀的 匹配索引值
        int suffixIndex = suffixArray[goodSuffixLength];
        if (suffixIndex != -1) {
            // 移动位数为 坏字符所在索引位置 - 当前后缀匹配到的索引位置 + 1
            return bcModelIndex - suffixIndex + 1;
        }
        // 遍历好后缀的后缀子串 ,区间为 坏字符所在索引位置往后移动两位之后到模式串的最后;
        for (int r = bcModelIndex + 2; r < modelLength; r++) {
            // 当suffix == -1 说明当前好后缀在 模式串的剩余子串中不存在能匹配的字符, 则需要判断后缀子串是否存在匹配的前缀子串
            // 后缀子串的长度为 模式串的长度 - 当前后缀子串起始字符的索引
            if (prefixArray[modelLength - r]) {
                // 如果当前后缀子串存在匹配的前缀子串,则移动位数为 r
                return r;
            }
        }
        return modelLength;
    }

    /**
     * @param modelString 模式串
     * @param suffix      存储 模式串后缀子串在模式串截取掉当前后缀子串的剩余子串能完全匹配的起始索引位置,数组的下标为后缀子串的长度
     * @param prefix      存储的为模式串的后缀子串和前缀子串进行匹配,如果能够匹配则代表当前后缀子串存在匹配的前缀子串,
     */
    public void generateGoodSuffix(char[] modelString, int[] suffix, boolean[] prefix) {
        int length = modelString.length;
        // 初始化suffix和prefix
        for (int i = 0; i < length; i++) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        // 计算模式串中所有好后缀子串 ,从最小后缀子串开始构建suffix
        for (int i = 0; i < length; i++) { // 从模式串的最后一个后缀子串开始对比
            // 模式串从前往后移动的前缀指针
            int preMoveIndex = i;
            //公共后缀子串长度
            int commonSuffixLength = 0;
            // 模式串最后一位从后往前遍历的索引
            int suffixMoveIndex = length - 1;
            /**
             * 这里计算suffix的逻辑为
             * commonSuffixLength 作为遍历[length - 1 - commonSuffixLength,length-1] 索引范围内从后往前
             * 和
             * preMoveIndex [0,preMoveIndex] 索引范围从后往前进行一一对比
             *
             *  例如 cabcab  当i < 2时,suffix 均没有变化都是默认值
             *  这里的公式均为数据运算
             *  当i = 2 时,
             *      第一次循环 preMoveIndex = 2 modelString[2] = b  modelString[length - 1 - commonSuffixLength] = modelString[6-1-0] = b ,进行while循环体内部, preMoveIndex-1 = 1 , commonSuffixLength +1 =1 , suffix[1] = 2
             *      第二次循环 preMoveIndex= 1 modelString[1] = a  modelString[length - 1 - commonSuffixLength] = modelString[6-1-1] = a , 进行while循环体内部 , preMoveIndex -1 = 0, commonSuffixLength +1 = 2,suffix[2] = 1
             *      第三次循环 preMoveIndex = 0 modelString[0] = c modelString[length - 1 - commonSuffixLength] = modelString[6-1-2] = c , 进行while循环体内部 , preMoveIndex -1 = -1, commonSuffixLength +1 = 3,suffix[3] = 0
             *
             * 每执行一次for循环都需要从模式串的最后一个字符开始比较,
             * 当存在字符匹配时,更新后缀子串长度对应的suffix下标中的对应的索引值(模式串从前往后的索引值 preMoveIndex) 最佳后缀子串的长度(commonSuffixLength)+1 两个指针(preMoveIndex 和 suffixMoveIndex)都开始向前移动进行对比;
             *
             * 之所以每执行一次for循环都需要从模式串的最后一个字符开始匹配,原因是为了得到后缀子串在 模式串排除后缀子串的剩余子串中的最大匹配的索引值
             *
             */
            while (preMoveIndex >= 0 && modelString[preMoveIndex] == modelString[suffixMoveIndex]) {
                --preMoveIndex;
                ++commonSuffixLength;
                suffix[commonSuffixLength] = preMoveIndex + 1;
            }
            // 当preMoveIndex变为-1时代表当前后缀子串存在最佳匹配前缀
            if (preMoveIndex == -1) {
                // 数组的下标为当前后缀子串的长度
                prefix[commonSuffixLength] = true;
            }

        }
    }

    /**
     * 将模式串转换为hash表, hash数组的下标为 字符对应的ascii码;数组中存储的值为当前字符在模式串中最大索引值
     * 方便badChar查找
     *
     * @param modelString 模式串 char数组
     * @return 模式串对应的散列表
     */
    public int[] generateCharHash(char[] modelString) {
        if (modelString == null) {
            return null;
        }
        int length = modelString.length;
        // 构建字符hash表,数组下标为字符的ASCII码,存储的数据为字符在模式串中存在的最大索引值
        int[] badCharArray = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            badCharArray[i] = -1;
        }
        for (int i = 0; i < length; i++) {
            // 获取字符对应的ascii码
            int ascii = modelString[i];
            // 将hash表中对应的数组下标
            badCharArray[ascii] = i;
        }
        return badCharArray;
    }

    public static void main(String[] args) {
        BoyerMooreDemo boyerMooreDemo = new BoyerMooreDemo();
        char[] mainString = "abcacabcbcbacabc".toCharArray();
        char[] modelString = "cbacabc".toCharArray();
        int i = boyerMooreDemo.boyerMoore(mainString, modelString);
        assert i == 8;
    }
}
