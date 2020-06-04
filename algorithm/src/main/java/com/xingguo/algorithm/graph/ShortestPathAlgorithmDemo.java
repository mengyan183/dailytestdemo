/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.graph;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import javax.xml.soap.Node;
import java.util.LinkedList;
import java.util.Objects;

/**
 * ShortestPathAlgorithmDemo
 * 最短路径算法
 * {@see Dijkstra}
 * {@see A*}
 * TODO : “迷宫问题” 是否可以 使用A*算法求解
 *
 * @author guoxing
 * @date 2020/5/25 10:02 AM
 * @since
 */
public class ShortestPathAlgorithmDemo {

    /**
     * 地图 对应数据结构, 有向带权图
     */
    private class Graph {
        // 图中顶点的个数
        private int v;
        // 邻接表
        private LinkedList<Edge>[] adj;

        public Graph(int v) {
            this.v = v;
            this.adj = new LinkedList[v];
            for (int i = 0; i < v; i++) {
                this.adj[i] = new LinkedList<>();
            }
        }

        /**
         * 构建图,添加边
         *
         * @param startVertxId 开始顶点
         * @param endVertxId   终止顶点
         * @param weight       权重
         */
        public void addEdge(int startVertxId, int endVertxId, int weight) {
            if (startVertxId < 0 || endVertxId < 0 || startVertxId >= v || endVertxId >= v) {
                throw new RuntimeException("数据不正确");
            }
            // startVertxId 顶点的邻接表中增加存在连接关系的边
            this.adj[startVertxId].add(new Edge(startVertxId, endVertxId, weight));
        }

        /**
         * 顶点之间的边
         */
        @AllArgsConstructor
        private class Edge {
            // 起始顶点序号
            private final int startVertxId;
            // 终止顶点序号
            private final int endVertxId;
            // 边的权重
            private final int weight;
        }

        // 下面这个类是为了dijkstra实现用的
        private class Vertex {
            // 顶点编号ID
            public int id;
            // 从起始顶点到这个顶点的距离
            public int dist = Integer.MAX_VALUE;
            // 预估距离  = dist + 曼哈顿距离
            public int predictDist = Integer.MAX_VALUE;
            // 顶点的横纵坐标;为了计算曼哈顿距离
            private int x, y;

            public Vertex(int id) {
                this.id = id;
            }

            public Vertex(int id, int dist) {
                this.id = id;
                this.dist = dist;
            }
        }

        // 因为Java提供的优先级队列，没有暴露更新数据的接口，所以我们需要重新实现一个
        private class PriorityQueue { // 根据vertex.dist构建小顶堆
            private Vertex[] nodes;
            private int count;

            public PriorityQueue(int v) {
                this.nodes = new Vertex[v + 1];
                this.count = v;
            }

            public Vertex poll() {
                if (count == 0) {
                    return null;
                }
                Vertex[] newNodes = new Vertex[count - 1];
                Vertex node = nodes[0];
                if (count - 1 >= 0) System.arraycopy(nodes, 1, newNodes, 0, count - 1);
                nodes = newNodes;
                count--;
                // TODO 堆化
                return node;
            }

            /**
             * TODO A* 和 dijkstra算法 而言 , A*算法构建小顶堆需要对比的是 估价距离 , 而 dijkstra算法需要对比的是 最短路径
             *
             * @param vertex
             */

            public void add(Vertex vertex) { // TODO: 堆中插入元素
            }

            // 更新结点的值，并且从下往上堆化，重新符合堆的定义。时间复杂度O(logn)。
            public void update(Vertex vertex) { // TODO: 留给读者实现...
            }

            public boolean isEmpty() {
                return (nodes == null || nodes.length == 0) && count == 0;
            }

            public void clear() {
                nodes = null;
                count = 0;
            }
        }

        /**
         * @param s 起始顶点
         * @param t 终止顶点
         */
        public void dijkstra(int s, int t) { // 从顶点s到顶点t的最短路径
            // 用来还原最短路径 ,
            int[] predecessor = new int[this.v];
            // 初始化所有顶点的最短路径距离
            Vertex[] vertexes = new Vertex[this.v];
            for (int i = 0; i < this.v; ++i) {
                vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
            }
            // 构建小顶堆,记录最短路径
            PriorityQueue queue = new PriorityQueue(this.v);// 小顶堆
            // 避免顶点重复记录
            boolean[] inqueue = new boolean[this.v]; // 标记是否进入过队列
            // 将起始顶点的路径设置为0
            vertexes[s].dist = 0;
            // 将起始顶点塞入堆中
            queue.add(vertexes[s]);
            // 标记当前顶点已被访问
            inqueue[s] = true;
            while (!queue.isEmpty()) {
                // 将堆顶元素弹出.并从堆中删除当前元素
                Vertex minVertex = queue.poll(); // 取堆顶元素并删除
                // 如果弹出的堆顶元素为目标顶点,则表示已找到最短路径
                if (minVertex.id == t) break; // 最短路径产生了
                // 遍历弹出顶点的邻接表
                for (Edge e : adj[minVertex.id]) {
                    /**
                     * 这里是进行对比 minVertex开始到nextVertex的所有决策中的最优解
                     */
                    //获取连接顶点的边数据
                    Vertex nextVertex = vertexes[e.endVertxId]; // minVertex-->nextVertex
                    if (minVertex.dist + e.weight < nextVertex.dist) { // 更新next的dist
                        nextVertex.dist = minVertex.dist + e.weight;
                        // 记录路径前置顶点
                        predecessor[nextVertex.id] = minVertex.id;
                        // 判断顶点是否已进入过队列(是否被访问过)
                        if (inqueue[nextVertex.id]) {
                            // 如果已经被访问过则更新当前顶点
                            queue.update(nextVertex); // 更新队列中的dist值
                        } else {
                            // 往队列中插入顶点
                            queue.add(nextVertex);
                            // 记录当前顶点已被访问
                            inqueue[nextVertex.id] = true;
                        }
                    }
                }
            }
            // 输出最短路径
            System.out.print(s);
            print(s, t, predecessor);
        }

        /**
         * A* 算法
         * 1:从起始顶点s开始,将s塞入小顶堆中,并将其最短路径以及估值路径都设置0
         * 2:遍历小顶堆,计算出队顶点相邻顶点的 估价(顶点之间的权重+顶点到终止顶点的曼哈顿距离) 塞入小顶堆中,并标记当前顶点已被访问过;如果顶点重复访问,则需要进行对比最短路径以及如果最短路径更小则更新堆中的数据
         * 3:只要到达终点就结束循环操作
         *
         * @param s 起始顶点
         * @param t 终止顶点
         */
        private void aStar(int s, int t) {
            // 用来还原最短路径 ,
            int[] predecessor = new int[this.v];
            // 初始化所有顶点的最短路径距离
            Vertex[] vertexes = new Vertex[this.v];
            for (int i = 0; i < this.v; ++i) {
                vertexes[i] = new Vertex(i);
            }
            // 构建小顶堆,记录最短路径
            PriorityQueue queue = new PriorityQueue(this.v);// 小顶堆
            // 避免顶点重复记录
            boolean[] inqueue = new boolean[this.v]; // 标记是否进入过队列
            // 将起始顶点的路径设置为0
            vertexes[s].dist = 0;
            vertexes[s].predictDist = 0;
            // 将顶点s插入到小顶堆中, A*算法小顶堆的构建按照估价距离进行对比
            queue.add(vertexes[s]);
            inqueue[s] = true;
            while (!queue.isEmpty()) {
                // 小顶堆的顶点出队
                Vertex poll = queue.poll();
                // 当前出队顶点id
                int id = poll.id;
                //获取当前顶点的所有邻接顶点
                LinkedList<Edge> edges = adj[id];
                if (CollectionUtils.isNotEmpty(edges)) {
                    Vertex vertex = vertexes[id];
                    for (Edge edge : edges) {
                        Objects.requireNonNull(edge, "邻接边不能为空");
                        int endVertxId = edge.endVertxId;
                        Vertex nextVertex = vertexes[endVertxId];
                        int nextDist = vertex.dist + edge.weight;
                        // 由于 nextVertex顶点的曼哈顿距离是恒定的 ,
                        // 因此对于 nextVertex.dist > nextDist 肯定满足  nextVertex.dist + manhattanDistance(nextVertex, vertexes[t]) > nextDist + manhattanDistance(nextVertex, vertexes[t]) ;nextVertex.dist + manhattanDistance(nextVertex, vertexes[t]) == nextVertex.predictDist
                        if (nextVertex.dist > nextDist) {
                            // 计算估价距离;
                            nextVertex.predictDist = nextVertex.dist + manhattanDistance(nextVertex, vertexes[t]);
                            nextVertex.dist = nextDist;
                            // 设置路径前置节点
                            predecessor[endVertxId] = id;
                            // 已经遍历过当前节点
                            if (inqueue[endVertxId]) {
                                // 更新小顶堆中的节点
                                queue.update(nextVertex);
                            } else {
                                // 往堆中插入顶点
                                queue.add(nextVertex);
                                // 记录节点已被操作
                                inqueue[endVertxId] = true;
                            }
                        }
                        /**
                         * 这里使用了贪心算法原则,只要有一个决策到达终止顶点,则终止遍历
                         * 因此找到的路径不一定是最短路径
                         */
                        if (nextVertex.id == t) {
                            // 清除小顶堆中的数据,避免内存泄露,并终止while循环
                            queue.clear();
                            break;
                        }
                    }
                }
            }
            // 输出最短路径
            System.out.print(s);
            print(s, t, predecessor);
        }

        /**
         * 获取 顶点之间的曼哈顿距离
         *
         * @param nextVertex
         * @param vertex
         * @return
         */
        private int manhattanDistance(Vertex nextVertex, Vertex vertex) {
            return Math.abs(nextVertex.x - vertex.x) + Math.abs(nextVertex.y - vertex.y);
        }

        private void print(int s, int t, int[] predecessor) {
            if (s == t) return;
            print(s, predecessor[t], predecessor);
            System.out.print("->" + t);
        }


    }

}
