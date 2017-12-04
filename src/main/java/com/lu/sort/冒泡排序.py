# coding=utf-8
# 冒泡排序
def sort(array):
    for i in range(array.__len__() - 1, 0, -1):
        for j in range(i):
            if array[i] > array[j]:
                x = array[i]
                array[i] = array[j]
                array[j] = x
    return array

def sort2(array):
    for i in range(array.__len__()):
        for j in range(array.__len__()):
            if array[j] < array[i]:
                x = array[j]
                array[j] = array[i]
                array[i] = x
    return array

if __name__ == "__main__":
    array = [12, 35, 99, 18, 76]
    list = sort(array)
    print list