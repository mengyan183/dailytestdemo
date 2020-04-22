/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.huffman.code;

import com.sun.tools.javac.util.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * HuffmanCodeDemo
 *
 * @author guoxing
 * @date 2020/4/21 9:51 AM
 * @since
 */
public class HuffmanCodeDemo {
    // 头节点
    private TreeNode root;
    // 所有节点的数组
    private TreeNode[] nodes;

    /**
     * 定义树子节点
     */
    static class TreeNode implements Comparable<TreeNode> {
        // 权重
        int weight;
        // 节点对应的二进制编码
        String code;
        // 左节点
        TreeNode left;
        // 右节点
        TreeNode right;

        public TreeNode(int weight) {
            this.weight = weight;
        }

        public TreeNode(int weight, TreeNode left, TreeNode right) {
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        /**
         * 对比权重值
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(TreeNode o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    /**
     * 根据权重值构建哈夫曼树
     *
     * @param weights
     */
    public void createHuffmanTree(int[] weights) {
        int length = weights.length;
        if (length < 1) {
            return;
        }
        nodes = new TreeNode[length];
        //优先队列
        PriorityQueue<TreeNode> queue = new PriorityQueue<>(length);
        for (int i = 0; i < length; i++) {
            TreeNode treeNode = new TreeNode(weights[i]);
            nodes[i] = treeNode;
            queue.add(treeNode);
        }
        // 循环队列
        while (queue.size() > 1) {
            // 从优先队列中依次获取最小的两个节点
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            assert right != null;
            assert left != null;
            // 构建父节点
            TreeNode parentNode = new TreeNode(left.weight + right.weight, left, right);
            queue.add(parentNode);
        }
        // 将最后一个节点设置为父节点
        root = queue.poll();
    }

    /**
     * 对树中所有节点设置code(每个节点对应的二进制编码)(递归方式)
     *
     * @param node 节点
     * @param code 二进制编码
     */
    public void encode(TreeNode node, String code) {
        if (node == null) {
            return;
        }
        // 设置当前节点的二进制值
        node.code = code;
        // 设置当前节点的左节点的值
        encode(node.left, node.code + "0");
        // 设置当前节点的右节点二进制值
        encode(node.right, node.code + "1");
    }

    /**
     * 获取节点的二进制值
     *
     * @param index
     * @return
     */
    public String coverHuffmanTreeCode(int index) {
        return nodes[index].code;
    }

    public static void main(String[] args) {
        char[] chars = {'A', 'B', 'C', 'D'};
        int[] weights = {2, 3, 4, 1};
        HuffmanCodeDemo huffmanCodeDemo = new HuffmanCodeDemo();
        // 构建哈弗曼树
        huffmanCodeDemo.createHuffmanTree(weights);
        // 设置每个节点对应的二进制数据
        huffmanCodeDemo.encode(huffmanCodeDemo.root, "");
        int length = chars.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String s = huffmanCodeDemo.coverHuffmanTreeCode(i);
            System.out.printf("%s对应的二进制编码为:%s; %n", chars[i], s);
            stringBuilder.append(s);
        }
        String s = stringBuilder.toString();
        System.out.printf("存储的二进制编码为:%s;%n", s);

        String randomS = "ahdasdhajdhajdakdajsda";
        char[] charArray = randomS.toCharArray();
        int charLength = charArray.length;
        ArrayList<Character> characters = new ArrayList<>();
        for (int i = 0; i < charLength; i++) {
            characters.add(charArray[i]);
        }
        Map<Character, List<Character>> collect = characters.stream().collect(Collectors.groupingBy(Character::new));
        System.out.println(collect);
    }

}