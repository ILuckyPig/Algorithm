package com.lu.sort;

/**
 * 希尔排序
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] list = {9, 1, 2, 5, 7, 4, 8, 6, 3, 5};
        shellSort(list, list.length);
    }

    public static void printAll(int[] list) {
        for (int i : list) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }

    public static void shellSort(int[] list, int g) {
        // 增量序列gap的取法必须满足：最后一个步长必须是1
        int gap = g / 2;
        System.out.format("gap = %d:\t", gap);
        // 直接插入排序
        for (int i = gap; i < list.length; i++) {
            int temp = list[i];
            int j = 0;
            // 对距离为 gap 的元素组进行排序
            for (j = i - gap; j >= 0 && temp < list[j]; j-=gap) {
                list[j + gap] = list[j];
            }
            list[j + gap] = temp;
        }
        // 当gap-步长为1时，结束排序
        if (gap != 1) {
            printAll(list);
            shellSort(list, gap);
        } else {
            printAll(list);
        }
    }
}
