/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.sort;

import java.util.LinkedList;
import java.util.Queue;

/**
 * TopologicalSort
 * 拓扑排序
 *
 * @author guoxing
 * @date 2020/5/24 3:36 PM
 * @since
 */
public class TopologicalSort {

    /**
     * 图
     */
    static class Graph {
        private int v; // 图顶点的个数
        private LinkedList<Integer> adj[]; // 邻接表

        public Graph(int v) {
            this.v = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                adj[i] = new LinkedList<>();
            }
        }

        //添加顶点为边的关系
        public void addEdge(int s, int t) { // 有向图
            adj[s].add(t);
        }

        /**
         * kahn算法 实现拓扑排序
         */
        public void kahn() {
            // 统计每个顶点的入度,数组的索引代表每个顶点
            int[] inDegree = new int[v];
            // 遍历所有的顶点
            for (int i = 0; i < v; i++) {
                // 当前顶点所有连接的顶点
                LinkedList<Integer> linkedPoint = adj[i];
                if (linkedPoint == null) {
                    continue;
                }
                linkedPoint.forEach(pointV -> {
                    if (pointV == null) {
                        return;
                    }
                    inDegree[pointV]++;
                });
            }
            //记录全部入度为0的节点
            Queue<Integer> inDegreeZeroPointQueue = new LinkedList<>();
            for (int i = 0; i < v; i++) {
                if (inDegree[i] == 0) {
                    inDegreeZeroPointQueue.add(i);
                }
            }
            while (!inDegreeZeroPointQueue.isEmpty()) {
                // 输出入度为0的顶点
                Integer poll = inDegreeZeroPointQueue.remove();
                System.out.print(poll + " -> ");
                // 获取 poll 连接的所有顶点
                LinkedList<Integer> integers = adj[poll];
                for (Integer point : integers) {
                    // 将当前顶点所有连接的顶点的入度都减一
                    inDegree[point]--;
                    if (inDegree[point] == 0) {
                        inDegreeZeroPointQueue.add(point);
                    }
                }
            }
        }

        // TODO DFS
        public void dfs(){

        }

        //TODO 有向环图监测
        public boolean verifyCycle(){
            return false;
        }
    }

    public static void main(String[] args) {
        // 一个6个顶点的有向图
        // 0 -> 1 , 0 -> 2
        // 1 -> 3
        // 2 -> 1,2 -> 3,2 -> 4
        // 3 -> 5
        // 4 -> 5

        // 该图的邻接表为 :
        // 0 : 1->2
        // 1 : 3
        // 2 : 1->3->4
        // 3 : 5
        // 4 : 5
        // 该图的逆邻接表(所以有可以到达的顶点列表)为:
        // 0 :
        // 1 : 0->2
        // 2 : 0
        // 3 : 1->2
        // 4 : 2
        // 5 : 3->4
        Graph graph = new Graph(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 1);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);

        graph.kahn();
    }
}
