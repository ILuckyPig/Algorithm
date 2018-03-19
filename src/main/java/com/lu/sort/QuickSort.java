package com.lu.sort;

/**
 * 快速排序
 */
public class QuickSort {

    private static void quickSort(int[] list, int left, int right) {
        if (left > right) {
            return;
        }
        int temp = list[left]; // 设置最左边为基准数
        int i = left;
        int j = right;
        while (i != j) {
            while (list[j] >= temp && i < j) { // 如果list[j]大于等于基准数，且i < j，j继续左移
                j--;
            }
            while (list[i] <= temp && i < j) {
                i++;
            }
            if (i < j) { // 如果i和j未相遇，交换i位置的数与j的数
                int t = list[i];
                list[i] = list[j];
                list[j] = t;
            }
        }

        // 最终基准数与相遇位置的数交换
        list[left] = list[i];
        list[i] = temp;

        quickSort(list, left, i - 1);
        quickSort(list, i + 1, right);
    }

    public static void main(String[] args) {
        int[] list = {12, 35, 99, 18, 76, 435, 77, 23, 4, 57, 89, 100, 8, 6, 9, 20, 3, 4, 56, 78};
        quickSort(list, 0, list.length - 1);
        for (int i : list) {
            System.out.println(i);
        }
    }
}
