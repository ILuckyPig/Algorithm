package com.lu.sort;

/**
 * 插入排序
 * 插入排序：每一趟将一个待排序的记录，按照其关键字的大小插入到有序队列的合适位置里，知道全部插入完成
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] list = {6, 3, 3, 5, 6, 3, 1, 0, 6, 4};
        int[] result = insertSort(list);
    }

    // 打印序列
    public static void printPart(int[] list, int begin, int end) {
        for (int i = 0; i < begin; i++) {
            System.out.print("\t");
        }
        for (int i = begin; i <= end; i++) {
            System.out.print(list[i] + "\t");
        }
        System.out.println();
    }

    public static int[] insertSort(int[] list) {
        System.out.printf("i = %d:\t", 0);
        printPart(list, 0, 0);
        // 第1个数肯定是有序的，从第2个数开始遍历，依次插入有序序列
        for (int i = 1; i < list.length; i++) {
            // 取出第i个数，和前i-1个数比较后，插入合适位置
            int temp = list[i];
            int j = 0;
            // 因为前i-1个数都是从小到大的有序序列，所以只要当前比较的数(list[j])比temp大，就把这个数后移一位
            for (j = i - 1; j >= 0 && temp < list[j] ; j--) {
                list[j + 1] = list[j];
            }
            list[j + 1] = temp;
            System.out.printf("i = %d:\t", i);
            printPart(list, 0, i);
        }
        return list;
    }
}
