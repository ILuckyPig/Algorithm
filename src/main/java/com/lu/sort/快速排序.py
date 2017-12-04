# coding=utf-8
import time
print time.time()
def sort(array, left, right):
    if (left > right):
        return
    i = left
    j = right
    temp = array[left]
    while (i != j):
        # 顺序很重要，必须右边先开始移动
        # 如果array[j]大于基准数且i < j，则j继续左移
        while array[j] >= temp and i < j:
            j-=1
        # 如果array[i]小于基准数且i < j，则i继续右移
        while array[i] <= temp and i < j:
            i+=1
        # 交换i和j位置的数
        if i < j:
            x = array[i]
            array[i] = array[j]
            array[j] = x
    # 交换基准数和ij相遇位置的数
    array[left] = array[i]
    array[i] = temp
    # 递归基准数左边的数组
    sort(array, left, i - 1)
    # 递归基准数右边的数组
    sort(array, j + 1, right)

# array = [12, 35, 99, 18, 76]
array = [12, 35, 99, 18, 76, 435, 77, 23, 4, 57, 89, 100, 8, 6, 9, 20, 3, 4, 56, 78]
sort(array, 0, 4)
print array
print time.time()
