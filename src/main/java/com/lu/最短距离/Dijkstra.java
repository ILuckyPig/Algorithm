package com.lu.最短距离;

/**
 * Dijkstra最短路径算法是一种单源最短路径，针对的是非负权边。所谓单源最短路径就是指定一个出发顶点，计算从该源顶点出发到其他所有顶点的最短路径.
 */
public class Dijkstra {
    // N表示距离无穷远
    static int N = 1000;
    static int[][] Graph = {
            {0, 6, 3, N, N, N},
            {6, 0, 2, 5, N, N},
            {3, 2, 0, 3, 4, N},
            {N, 5, 3, 0, 2, 3},
            {N, N, 4, 2, 0, 5},
            {N, N, N, 3, 5, 0}
    };

    static void dijkstra(int vs, int[][] Graph) {
        int NUM = Graph[0].length;
        // 存储每个最短路径末尾节点的前一个节点
        int[] preNode = new int[NUM];
        // 最短路径，index表示哪个节点，value表示最短路径的值
        int[] minDist = new int[NUM];
        // 标记是否已经找到了到达该节点最短路径，true为找到；false为未找到
        boolean[] find = new boolean[NUM];

        int vnear = 0;

        // 初始化
        for (int i = 0; i < minDist.length; i++) {
            preNode[i] = i;
            minDist[i] = Graph[vs][i];
            find[i] = false;
        }

        // 表示出发节点已找到最短路径（0）
        find[vs] = true;


        for (int i = 0; i < Graph.length; i++) {
            // 每次循环求得距离vs最近的节点vnear和最短距离min
            int min = N;
            for (int j = 0; j < Graph.length; j++) {
                if (!find[j] && minDist[j] < min) {
                    min = minDist[j];
                    vnear = j;
                }
            }
            find[vnear] = true;

            // 根据vnear更新vs到其他节点的前节点以及到其他节点的距离
            for (int k = 0; k < Graph.length; k++) {
                if (!find[k] && (min + Graph[vnear][k] < minDist[k])) {
                    minDist[k] = min + Graph[vnear][k];
                    preNode[k] = vnear;
                }
            }
        }

        for (int i = 0; i < NUM; i++) {
            System.out.printf("v%d...v%d --> v%d, s=%d\n",vs,preNode[i],i,minDist[i]);
        }
    }

    public static void main(String[] args) {
        dijkstra(0, Graph);
    }
}
