package com.lu.sort;

import static com.lu.sort.InsertSort.printPart;

public class HeapSort {
    public static void main(String[] args) {
        int[] list = {1, 3, 4, 5, 2, 6, 9, 7, 8, 0};
        heapSort(list);
    }

    public static void createHeap(int[] list, int parent, int length) {
        int temp = list[parent];    // temp保存当前父节点
        int child = parent * 2 + 1; // 先获得左子节点

        while (child < length) {
            if (child + 1 < length && list[child] < list[child + 1]) {
                child++;
            }

            if (temp >= list[child]) {
                break;
            }

            list[parent] = list[child];

            parent = child;
            child = 2 * child + 1;
        }

        list[parent] = temp;
    }

    public static void heapSort(int[] list) {
        for (int i = list.length / 2; i >= 0 ; i--) {
            createHeap(list, i, list.length);
        }

        for (int i = list.length - 1; i > 0 ; i--) {
            int temp = list[i];
            list[i] = list[0];
            list[0] = temp;
            createHeap(list,0,i);
            System.out.format("第 %d 趟：\t", list.length - i);
            printPart(list, 0, list.length - 1);
        }

    }
}
