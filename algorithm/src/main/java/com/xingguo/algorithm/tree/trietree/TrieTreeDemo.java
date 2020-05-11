/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.tree.trietree;

import lombok.Data;
import org.apache.commons.collections4.trie.PatriciaTrie;

/**
 * TrieTreeDemo
 * 字符串查找 trieTree
 * {@link PatriciaTrie}
 *
 * @author guoxing
 * @date 2020/5/11 11:40 AM
 * @since
 */
public class TrieTreeDemo {

    public static void main(String[] args) {
        TrieTreeDemo trieTreeDemo = new TrieTreeDemo();
        String test = "test";
        char[] chars = test.toLowerCase().toCharArray();
        trieTreeDemo.insert(chars);
        System.out.println(trieTreeDemo.find(chars));
        System.out.println(trieTreeDemo.find("tes".toLowerCase().toCharArray()));
    }

    /**
     * 小写英文字母 TrieTree
     */
    @Data
    private class TrieTree {
        private char data;
        private TrieTree[] children = new TrieTree[26];
        private boolean isEnd = false;

        public TrieTree(char data) {
            this.data = data;
        }
    }

    private TrieTree root = new TrieTree('/');

    /**
     * 插入字符串
     *
     * @param string
     */
    public void insert(char[] string) {
        if (string == null || string.length < 1) {
            return;
        }
        TrieTree p = root;
        for (int i = 0; i < string.length; i++) {
            char c = string[i];
            int index = c - 'a';
            if (p.children[index] == null) {
                TrieTree child = new TrieTree(c);
                p.children[index] = child;
                p = child;
            } else {
                p = p.children[index];
            }
        }
        p.isEnd = true;
    }

    /**
     * 查找字符串
     *
     * @param string
     * @return
     */
    public boolean find(char[] string) {
        if (string == null || string.length < 1) {
            return false;
        }
        TrieTree p = root;
        for (int i = 0; i < string.length; i++) {
            char c = string[i];
            int index = c - 'a';
            if (p.children[index] == null) {
                return false;
            }
            p = p.children[index];
        }
        return p.isEnd;
    }
}
