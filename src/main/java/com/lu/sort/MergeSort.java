package com.lu.sort;

/**
 * 归并排序
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] list = {9, 1, 5, 3, 4, 2, 6, 8, 7};
        System.out.print("排序前:\t\t");
        printAll(list);
        sort(list);
        System.out.print("排序后:\t\t");
        printAll(list);
    }

    public static void merge(int[] list, int low, int mid, int high) {
        int i = low;        // 左数组指针
        int j = mid + 1;    // 右数组指针
        int index = 0;      // 临时数组指针
        int[] R2 = new int[high - low + 1]; // 临时数组
        // i小于左数组，j小于右数组
        while (i <= mid && j <= high) {
            // 如果list[i]即左数组i小于list[j]即右数组j
            // 就把list[i]放入临时数组
            if (list[i] <= list[j]) {
                R2[index] = list[i];
                i++;
                index++;
            // 否则把list[j]方法入临时数组
            } else {
                R2[index] = list[j];
                j++;
                index++;
            }
        }
        // 将左边剩余元素填充进临时数组中
        while (i <= mid) {
            R2[index] = list[i];
            i++;
            index++;
        }
        // 将右边剩余元素放入临时数组中
        while (j <= high) {
            R2[index] = list[j];
            j++;
            index++;
        }
        // 将合并后的数组复制到原数组中
        for (index = 0, i = low; i <= high; i++, index++) {
            list[i] = R2[index];
        }
    }

    public static void mergePass(int[] list, int gap, int length) {
        int i = 0;
        // 归并gap长度的两个相邻子表
        for (i = 0; i + 2 * gap - 1 < length; i = i + 2 * gap) {
            merge(list, i, i + gap - 1, i + 2 * gap - 1);
        }
        // 余下两个子表，后者长度小于gap
        if (i + gap - 1 < length) {
            merge(list, i, i + gap - 1, length - 1);
        }
    }

    public static int[] sort(int[] list) {
        for (int gap = 1; gap < list.length; gap = 2 * gap) {
            mergePass(list, gap, list.length);
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
