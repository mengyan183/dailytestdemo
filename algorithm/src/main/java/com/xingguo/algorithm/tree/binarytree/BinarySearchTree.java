/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.tree.binarytree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * BinarySearchTree
 * 二叉查找树
 *
 * @author guoxing
 * @date 2020/5/2 8:54 PM
 * @since
 */
@Slf4j
public class BinarySearchTree {
    // 根节点
    private Node root;

    @AllArgsConstructor
    @Getter
    @Setter
    static class Node {
        // 数据
        @NonNull
        private int data;

        private Node left;

        private Node right;
    }

    /**
     * 查找不存在重复数据的二叉查找树中值相等的节点
     *
     * @param value
     * @return
     */
    public Node findByData(final int value) {
        if (root == null) {
            return null;
        }
        Node p = root;
        while (p != null) {
            if (value == p.getData()) {
                return p;
            } else {
                // 查找左子树
                if (value < p.getData()) {
                    p = p.getLeft();
                } else {
                    p = p.getRight();
                }
            }
        }
        return null;
    }

    /**
     * 插入操作
     *
     * @param value
     */
    public void insert(final int value) {
        if (root == null) {
            root = new Node(value, null, null);
            return;
        }
        Node p = root;
        while (p != null) {
            if (value == p.getData()) {
                log.error("不允许插入重复数据");
                return;
            } else {
                // 查找左子树
                if (value < p.getData()) {
                    if (p.getLeft() == null) {
                        // 设置为当前节点的左节点
                        p.setLeft(new Node(value, null, null));
                        break;
                    }
                    p = p.getLeft();
                } else {
                    if (p.getRight() == null) {
                        p.setRight(new Node(value, null, null));
                        break;
                    }
                    p = p.getRight();
                }
            }
        }
    }

    /**
     * 删除时存在三种情况
     * 1: 当前树中不存在和要删除节点值相同的节点,返回空
     * 2: 匹配到要删除的节点只有一个子节点, 当前节点的子节点直接替换当前节点完成删除操作
     * 3: 当前要删除的节点存在两个子节点,则查找当前节点的右子树中对的最小节点替换当前节点,实现当前节点删除
     *
     * @param data
     */
    public void delete(int data) {
        Node p = root; // p指向要删除的节点，初始化指向根节点
        Node pp = null; // pp记录的是p的父节点
        while (p != null && p.data != data) {
            pp = p;
            if (data > p.data) p = p.right;
            else p = p.left;
        }
        if (p == null) return; // 没有找到

        // 要删除的节点有两个子节点
        if (p.left != null && p.right != null) { // 查找右子树中最小节点
            Node minP = p.right;
            Node minPP = p; // minPP表示minP的父节点
            while (minP.left != null) {
                minPP = minP;
                minP = minP.left;
            }
            p.data = minP.data; // 将minP的数据替换到p中
            p = minP; // 下面就变成了删除minP了
            pp = minPP;
        }

        // 删除节点是叶子节点或者仅有一个子节点
        Node child; // p的子节点
        if (p.left != null) child = p.left;
        else if (p.right != null) child = p.right;
        else child = null;

        if (pp == null) root = child; // 删除的是根节点
        else if (pp.left == p) pp.left = child;
        else pp.right = child;
    }

    //TODO 重复二叉查找树相关操作
}
