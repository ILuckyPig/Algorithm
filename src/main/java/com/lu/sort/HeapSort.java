package com.lu.sort;

import static com.lu.sort.InsertSort.printPart;

/**
 * 堆排序
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] list = {1, 3, 4, 5, 2, 6, 9, 7, 8, 0, 9};
        heapSort(list);
    }

    /**
     * 调整大顶堆
     * @param list
     * @param parent
     * @param length
     */
    public static void adjustHeap(int[] list, int parent, int length) {
        int temp = list[parent];    // temp保存当前父节点
        int child = parent * 2 + 1; // 先从左子节点开始，也就是2i+1开始

        while (child < length) {
            if (child + 1 < length && list[child] < list[child + 1]) { // 如果左子节点小于右子节点，则child指向右子节点
                child++;
            }

            if (temp >= list[child]) { // 如果子节点小于父节点，不做处理
                break;
            }

            list[parent] = list[child];// 如果子节点大于父节点，则交换子节点和父节点的值
            parent = child;

            child = 2 * child + 1;
        }

        list[parent] = temp;// 将temp值放到最终位置
    }

    public static void heapSort(int[] list) {
        // 构建大顶堆
        for (int i = list.length / 2 - 1; i >= 0 ; i--) {
            // 从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(list, i, list.length);
        }

        // 调整堆结构+交换堆顶元素与末尾元素
        for (int i = list.length - 1; i > 0 ; i--) {
            int temp = list[i];// 交换堆顶元素与末尾元素
            list[i] = list[0];
            list[0] = temp;
            adjustHeap(list,0,i);// 重新调整大顶堆
            System.out.format("第 %d 趟：\t", list.length - i);
            printPart(list, 0, list.length - 1);
        }

    }
}
