package com.lu.sort;

/**
 * 归并排序
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] list = {9, 1, 5, 3, 4, 2, 6, 8, 7};
        sort(list);
    }

    public static void mergeSort(int[] list, int low, int mid, int high) {
        int i = low;
        int j = mid + 1;
        int index = 0;      // 临时数组下标
        int[] R2 = new int[high - low + 1]; // 临时数组
        while (i <= mid && j <= high) {
            if (list[i] <= list[j]) {
                R2[0] = list[i];
                i++;
                index++;
            } else {
                R2[0] = list[j];
                j++;
                index++;
            }
        }
        while (i <= mid) {
            R2[index] = list[i];
            i++;
            index++;
        }
        while (j <= high) {
            R2[index] = list[j];
            j++;
            index++;
        }
        for (index = 0, i = low; i <= high; i++, index++) {
            list[i] = R2[index];
        }
    }

    public static void merge(int[] list, int gap, int length) {
        int i = 0;
        for (i = 0; i + 2 * gap - 1 < length; i = i + 2 * length) {
            mergeSort(list, i, i + gap - 1, i + 2 * gap - 1);
        }
        if (i + gap - 1 < length) {
            mergeSort(list, i, i + gap - 1, length - 1);
        }
    }

    public static int[] sort(int[] list) {
        for (int gap = 1; gap < list.length; gap = 2 * gap) {
            merge(list, gap, list.length);
            System.out.print("gap = " + gap + ":\t");
            printAll(list);
        }
        return list;
    }

    public static void printAll(int[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i] + "\t");
        }
        System.out.println();
    }
}
