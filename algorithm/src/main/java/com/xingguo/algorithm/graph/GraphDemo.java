/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.graph;

import sun.java2d.opengl.OGLRenderQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * GraphDEMO
 * 无向图
 *
 * @author guoxing
 * @date 2020/5/12 9:58 PM
 * @since
 */
public class GraphDemo {


    private static class Graph { // 无向图
        private int v; // 顶点的个数
        private LinkedList<Integer> adj[]; // 邻接表

        public Graph(int v) {
            this.v = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                adj[i] = new LinkedList<>();
            }
        }

        //添加顶点为边的关系
        public void addEdge(int s, int t) { // 无向图一条边存两次
            adj[s].add(t);
            adj[t].add(s);
        }

        /**
         * 广度优先搜索查询 在无向图存在的指定值
         *
         * @return
         */
        public boolean bfsV1(int from, int to) {
            if (from == to) {
                return true;
            }
            if (from >= v || to >= v) {
                return false;
            }
            if (adj == null || adj.length == 0) {
                return false;
            }
            // 访问过的顶点记录
            boolean[] visited = new boolean[v];
            visited[from] = true;
            // 待访问顶点队列
            Queue<Integer> waitVisit = new LinkedList<>();
            waitVisit.add(from);
            // 访问顶点的前置顶点 索引值
            int[] prev = new int[v];
            //初始化prev
            for (int i = 0; i < v; i++) {
                prev[i] = -1;
            }
            boolean founded = false;
            while (!waitVisit.isEmpty()) {
                Integer poll = waitVisit.poll();
                LinkedList<Integer> subRelation = adj[poll];
                if (subRelation == null || subRelation.isEmpty()) {
                    return false;
                }
                // 遍历连接的顶点
                for (Integer index : subRelation) {
                    // 如果顶点索引为空或该索引的顶点已被访问过,则继续遍历
                    if (index == null || visited[index]) {
                        continue;
                    }
                    visited[index] = true;
                    prev[index] = poll;
                    if (index.equals(to)) {
                        founded = true;
                        break;
                    }
                    waitVisit.add(index);
                }
            }
            if (founded) {
                print(prev, from, to);
            }
            return founded;
        }


        public void bfs(int s, int t) {
            if (s == t) return;
            // 顶点访问记录
            boolean[] visited = new boolean[v];
            visited[s] = true;
            // 待访问顶点队列
            Queue<Integer> queue = new LinkedList<>();
            queue.add(s);
            int[] prev = new int[v];
            for (int i = 0; i < v; ++i) {
                prev[i] = -1;
            }
            while (queue.size() != 0) {
                int w = queue.poll();
                // 遍历当前顶点中邻接表中的连接的顶点
                for (int i = 0; i < adj[w].size(); ++i) {
                    int q = adj[w].get(i);
                    // 判断该顶点是否被访问过
                    if (!visited[q]) {
                        // 设置q的前置顶点访问的索引为 w
                        prev[q] = w;
                        // 判断是否为要查找的索引值
                        if (q == t) {
                            print(prev, s, t);
                            return;
                        }
                        // 增加当前顶点访问记录
                        visited[q] = true;
                        // 队列中增加待访问节点
                        queue.add(q);
                    }
                }
            }
        }

        /**
         * 深度优先查询
         * //TODO
         *
         * @param from
         * @param to
         * @return
         */
        public boolean dfs(int from, int to) {
            return false;
        }

        private void print(int[] prev, int s, int t) { // 递归打印s->t的路径
            if (prev[t] != -1 && t != s) {
                print(prev, s, prev[t]);
            }
            System.out.print(t + " ");
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int j = random.nextInt(4);
            if (i == j) {
                continue;
            }
            graph.addEdge(i, j);
        }
        graph.bfs(0, 4);
        System.out.println("=================");
        boolean b = graph.bfsV1(0, 4);
        System.out.println(b);
    }


}
