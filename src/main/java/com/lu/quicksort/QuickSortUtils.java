package com.lu.quicksort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 快速排序工具类
 */
public class QuickSortUtils {
    int partition(List<HashMap<String, Integer>> list, int left, int right) {
        Integer[] a = new Integer[1];
        int tmp = list.get(left).values().toArray(a)[0];
        int i = left;
        int j = right;
        while (i != j) {
            while (i < j && list.get(j).values().toArray(a)[0] >= tmp) {
                j--;
            }
            while (i < j && list.get(i).values().toArray(a)[0] <= tmp) {
                i++;
            }
            if (i < j) {
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, left, i);
        return i;
    }

    public void quickSort(List<HashMap<String, Integer>> list, int left, int right) {
        int loc = 0;
        if (left < right) {
            loc = partition(list, left, list.size() - 1);
            quickSort(list, left, loc - 1);
            quickSort(list,loc + 1, right);
        }
    }

    public static void main(String[] args) {
        QuickSortUtils q = new QuickSortUtils();

        HashMap<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("1.2.3", 123);
        HashMap<String, Integer> map2 = new HashMap<String, Integer>();
        map2.put("2.2.5", 325);
        HashMap<String, Integer> map3 = new HashMap<String, Integer>();
        map3.put("90.2.3", 107);
        HashMap<String, Integer> map4 = new HashMap<String, Integer>();
        map4.put("4.5.3", 90);
        HashMap<String, Integer> map5 = new HashMap<String, Integer>();
        map5.put("43.6.7", 1);
        HashMap<String, Integer> map6 = new HashMap<String, Integer>();
        map6.put("43.6.90", 1);
        HashMap<String, Integer> map7 = new HashMap<String, Integer>();
        map7.put("43.6.88", 123);
        ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map6);
        list.add(map4);
        list.add(map5);
        list.add(map7);


        q.quickSort(list, 0, list.size() - 1);
        for (HashMap<String, Integer> item : list) {
            for (String s : item.keySet()) {
                System.out.println(s + "\t" + item.get(s));
            }
        }
    }
}
