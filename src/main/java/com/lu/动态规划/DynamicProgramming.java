package com.lu.动态规划;

/**
 *  两个字符串
 * str1 = abcdef
 * str2 = bcadxy
 * 利用增加、删除、修改 str2 ——> str1，最短需要几步
 */
public class DynamicProgramming {
    public static void main(String[] args) {
        String str1 = "abcdef";
        String str2 = "bcadxy";

        // edit[i][j]数组表示第一个字符串的前i个字符，第二个字符串的前j个字符，当前值表示str1(i)和str2(2)的编辑距离
        int[][] edit = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 0; i < str1.length() + 1; i++) {
            for (int j = 0; j < str2.length() + 1; j++) {
                // _ ——> _ 需要0步
                if (i == 0 && j == 0) {
                    edit[i][j] = 0;
                // str(i) ——> _ 需要i步
                } else if (i > 0 && j == 0) {
                    edit[i][j] = i;
                // _ ——> str(j) 需要j步
                } else if (i == 0 && j > 0) {
                    edit[i][j] = j;
                // str(i) ——> str(j)
                // 有三种情况
                // 1) edit[i-1][j] + 1
                // 2) edit[i][j-1] + 1
                // 3) edit[i-1][j-1] + (第i个字符 == 第j个字符 ? 0 : 1)
                // 取三数中的最小值
                // 最后edit[][]的左下角的值，为最短距离，即最少步骤
                } else if (i > 0 && j > 0) {
                    int x = edit[i - 1][j] + 1;
                    int y = edit[i][j - 1] + 1;
                    String s1 = str1.substring(i - 1, i);
                    String s2 = str2.substring(j - 1, j);
                    int z = edit[i - 1][j - 1] + (s1.equals(s2) ? 0 : 1);
                    int a = Math.min(x, y);
                    int b = Math.min(z, a);
                    edit[i][j] = b;
                }
            }
        }

        System.out.println(edit[str1.length()][str1.length()]);
    }
}
