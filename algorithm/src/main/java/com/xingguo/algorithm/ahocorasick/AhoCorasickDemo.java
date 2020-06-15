/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.ahocorasick;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AhoCorasickDemo
 * 多模式串匹配 AC自动机
 *
 * @author guoxing
 * @date 2020/6/10 1:18 PM
 * @since
 */
public class AhoCorasickDemo {
    // 设置根节点
    final AcNode ROOT = new AcNode(Character.MIN_VALUE);

    /**
     * ac自动机 节点存储的数据
     */
    class AcNode {
        // 节点中存储的具体数据
        public char data;
        // 子节点
        public AcNode[] children = new AcNode[26]; // 字符集只包含a~z这26个字符
        // 判断是否为结尾字符
        public boolean isEndingChar = false; // 结尾字符为true
        public int length = -1; // 当isEndingChar=true时，记录模式串长度
        // 提高字符串匹配效率
        public AcNode fail; // 失败指针

        public AcNode(char data) {
            this.data = data;
        }
    }

    /**
     * 使用ac自动机过滤敏感词
     * 1:使用多个敏感词模式串构建trie树
     * 2:并构建trie树中每个节点的失败指针
     * 3:使用trie树和主串进行对比并替换匹配到的敏感词为 "*"号,完成敏感词过滤功能
     *
     * @param mainString     主串
     * @param sensitiveWords 多组敏感词
     */
    public void filter(char[] mainString, char[][] sensitiveWords) {
        // 构建Trie树
        buildTrieTree(sensitiveWords);
        // 设置Trie树中的失败指针
        buildFailurePoint();
        // 使用ac自动机和主串进行匹配
        match(mainString);
    }

    /**
     * 使用多个敏感词构建trie树
     *
     * @param sensitiveWords
     */
    private void buildTrieTree(char[][] sensitiveWords) {
        for (char[] sensitiveWord : sensitiveWords) {
            insertTrieTree(sensitiveWord);
        }
    }

    /**
     * 往trie树中插入单个字符串
     *
     * @param sensitiveWord
     */
    private void insertTrieTree(char[] sensitiveWord) {
        AcNode p = ROOT;
        for (char c : sensitiveWord) {
            AcNode child = p.children[c - 'a'];
            if (child == null) {
                p.children[c - 'a'] = new AcNode(c);
            }
            p = p.children[c - 'a'];
        }
        p.isEndingChar = true;
        p.length = sensitiveWord.length;
    }

    public void match(char[] text) { // text是主串
        int n = text.length;
        AcNode p = ROOT;
        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != ROOT) {
                p = p.fail; // 失败指针发挥作用的地方
            }
            p = p.children[idx];
            if (p == null) p = ROOT; // 如果没有匹配的，从root开始重新匹配
            AcNode tmp = p;
            while (tmp != ROOT) { // 打印出可以匹配的模式串
                if (tmp.isEndingChar) {
                    // 匹配到了模式串
                    int pos = i - tmp.length + 1;
                    System.out.println("匹配起始下标" + pos + "; 长度" + tmp.length);
//                    replaceSensitiveWord(text,pos,tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }

    /**
     * Trie树节点的失败指针
     */
    private void buildFailurePoint() {
        Queue<AcNode> queue = new LinkedList<>();
        // 设置根节点的失败指针为NULL
        ROOT.fail = null;
        // 将根节点塞入到队列中
        queue.add(ROOT);
        // 遍历队列
        while (!queue.isEmpty()) {
            // 出队操作
            AcNode acNode = queue.remove();
            // 获取子节点
            AcNode[] children = acNode.children;
            // i<26原因为已限定ac节点中存储的字符数据范围为 a-z
            for (int i = 0; i < 26; i++) {
                AcNode child = children[i];
                if (child == null) {
                    continue;
                }
                if (ROOT.equals(acNode)) {
                    // 如果父节点为根节点则将该节点的失败指针指向根节点
                    child.fail = ROOT;
                } else {
                    // 获取acNode的失败节点
                    AcNode fail = acNode.fail;
                    // 从acNode的失败节点推导acNode的child的失败节点, 通过查找acNode失败节点的子节点中是否存在和child字符匹配的节点
                    // 如果不存在,则继续查找失败节点的失败节点直到找到ROOT(fail == null)
                    while (fail != null) {
                        // 判断child存储的字符在 acNode失败指针指向的节点的子节点中是否存在
                        AcNode failChild = fail.children[child.data - 'a'];
                        if (failChild != null) {
                            // 将child的失败指针指向可以匹配到的节点
                            child.fail = failChild;
                            break;
                        }
                        // 查询失败节点的失败节点
                        fail = fail.fail;
                    }
                    // 如果未找到失败节点,则将child的失败节点指向ROOT
                    if (fail == null) {
                        child.fail = ROOT;
                    }
                }
                // 将child节点的塞入到队列中
                queue.add(child);
            }
        }
    }

    public static void main(String[] args) {
        char[][] sensitiveWords = new char[][]{"abce".toCharArray(), "bcd".toCharArray(), "ce".toCharArray(),"ace".toCharArray(),"cef".toCharArray()};
        char[] mainWord = "acef".toCharArray();
        AhoCorasickDemo ahoCorasickDemo = new AhoCorasickDemo();
        ahoCorasickDemo.filter(mainWord, sensitiveWords);
        List<Date> overTime = Stream.of(new Date(),new Date(),new Date())
                .sorted(Comparator.comparing(Date::getTime).reversed())
                .collect(Collectors.toList());
        System.out.println(overTime);
    }
}
