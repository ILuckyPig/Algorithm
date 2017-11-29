package com.lu.widesearch;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class WideSearchPrime {
    public static void main(String[] args) {
        int a = 1033;
        int b = 8179;
        int[] list = run(a, b);
        step(list, a, b);
    }

    private static boolean isPrime(int num) {
        for (int i = 2; i < Math.sqrt(num); i++) {
            if (0 == num % i) {
                return false;
            }
        }
        return true;
    }

    private static void step(int[] list, int a, int b) {
        int x = list[b];
        System.out.println(b);
        if (a != x) {
            step(list, a, x);
        } else {
            System.out.println(a);
        }
    }

    private static int[] run(int x, int b) {
        Queue<Integer> queue = new ArrayBlockingQueue<>(10000);
        int[] list = new int[9999];
        queue.add(x);
        int j = 1;
        while (!queue.isEmpty()) {
//            System.out.println(j++);
            int a = queue.poll();
            if (a == b) {
                break;
            }
            int ge = a % 10;
            int shi = (a / 10) % 10;
            int bai = (a / 100) % 10;
            int qian = a / 1000;
            int num = 0;
            for (int i = 1; i <= 9; i+=2) {
                num = qian * 1000 + bai * 100 + shi * 10 + i;
                if (isPrime(num) && list[num] == 0) {
                    queue.add(num);
                    list[num] = a;
                }
            }
            for (int i = 0; i <= 9; i++) {
                num = qian * 1000 + bai * 100 + i * 10 + ge;
                if (isPrime(num) && list[num] == 0) {
                    queue.add(num);
                    list[num] = a;
                }
            }
            for (int i = 0; i <= 9; i++) {
                num = qian * 1000 + i * 100 + shi * 10 + ge;
                if (isPrime(num) && list[num] == 0) {
                    queue.add(num);
                    list[num] = a;
                }
            }
            for (int i = 1; i <= 9; i++) {
                num = i * 1000 + bai * 100 + shi * 10 + ge;
                if (isPrime(num) && list[num] == 0) {
                    queue.add(num);
                    list[num] = a;
                }
            }
        }
        return list;
    }
}
