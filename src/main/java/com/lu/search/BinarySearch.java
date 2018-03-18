package com.lu.search;

/**
 * 二分查找
 * 要求：
 *  （1）必须采用顺序存储结构
 *  （2）必须按关键字大小有序排列
 * 原理：
 *  将数组分为三部分，依次是中值（所谓的中值就是数组中间位置的那个值）前，中值，中值后；
 *  将要查找的值和数组的中值进行比较，若小于中值则在中值前面找，若大于中值则在中值后面找，等于中值时直接返回。
 *  然后依次是一个递归过程，将前半部分或者后半部分继续分解为三部分。
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {1, 3, 5, 5, 7, 10};
//        System.out.println(cycleBinarySearch(array, 71));
        System.out.println(cycleBinarySearch(array, 7));
    }

    /**
     * 循环实现
     * @param array 查询数组
     * @param x 查询的数
     * @return 存在返回x的index；否则返回-1
     */
    private static int cycleBinarySearch(int[] array, int x) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (x == array[mid]) {
                return mid;
            } else if (x < array[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 递归实现
     * @param array 查询数组
     * @param low 左
     * @param high 右
     * @param x 查询的数
     * @return 返回x的index；不存在返回-1
     */
    private static int recursionBinarySearch(int[] array, int low, int high, int x) {
        int mid = (low + high) / 2;
        if (low > high) {
            return -1;
        }
        if (x == array[mid]) {
            return mid;
        } else if (x > array[mid]) {
            return recursionBinarySearch(array, mid + 1, high, x);
        } else {
            return recursionBinarySearch(array, low, high - 1, x);
        }
    }
}
