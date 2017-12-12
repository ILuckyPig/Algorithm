package com.lu.sort;

/**
 * 简单选择排序
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] list = {9, 1, 2, 5, 7, 4, 8, 6, 3, 5};
        selectSort(list);
    }

    public static void selectSort(int[] list) {
        // 需要遍历获得最小值的次数
        // 要注意一点，当要排序 N 个数，已经经过 N-1 次遍历后，已经是有序数列，所以这里是list.length-1
        for (int i = 0; i < list.length - 1; i++) {
            // 用来保存这趟最小值的索引
            int minIndex = i;
            // 从第i+1开始寻找是否有比最小值还小的数
            for (int j = i + 1; j < list.length; j++) {
                // 如果存在，则将记录这个索引，即minIndex总是指向最小值
                if (list[minIndex] > list[j]) {
                    minIndex = j;
                }
            }
            // 如果最小值发生过改变，则将最小值与当前范围的第一个数交换
            if (minIndex != i) {
                int temp = list[i];
                list[i] = list[minIndex];
                list[minIndex] = temp;
            }

            System.out.printf("第%d趟:\t", i + 1);
            for (int l : list) {
                System.out.print(l + "\t");
            }
            System.out.println();
        }
    }
}
