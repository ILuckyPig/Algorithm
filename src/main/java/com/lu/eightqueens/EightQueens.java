package com.lu.eightqueens;

/**
 * 经典的八皇后问题
 */
public class EightQueens {
    // N皇后
    private static final int N = 10;
    // 记录解的个数
    private static int count = 0;

    /**
     * 检查左上、中上、右上是否安全
     * @param chess 棋盘
     * @param row 行
     * @param col 列
     * @return 安全返回true；否则返回false
     */
    private boolean isSafety(int[][] chess, int row, int col) {
        int step = 1;
        while (row - step >= 0) {
            if (chess[row - step][col] == 1) {// 检查中上是否有皇后，有，返回false
                return false;
            }
            if (col - step >= 0 && chess[row - step][col - step] == 1) {// 检查左上是否有皇后
                return false;
            }
            if (col + step < N && chess[row - step][col + step] == 1) {// 检查右上是否有皇后
                return false;
            }
            step++;
        }
        return true;
    }

    private void putQueen(int[][] chess, int row) {
        // 如果放置到最后一行，则停止放置，即递归终止
        if (row == N) {
            count++;
            System.out.println("第" + count + "情况");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print(chess[i][j] + " ");
                }
                System.out.println();
            }
            return;
        }
        int[][] chessCopy = chess.clone();

        // 尝试向这一行的每一格摆放皇后
        // 如果放置成功，则直接进入下一行
        for (int i = 0; i < N; i++) {
            // 当要摆放皇后之前，先把这一行清除干净，避免前一次摆放后影响到本次摆放
            for (int j = 0; j < N; j++) {
                chessCopy[row][j] = 0;
            }
            chessCopy[row][i] = 1;

            if (isSafety(chessCopy, row, i)) {
                putQueen(chessCopy, row + 1);
            }
        }
    }

    public static void main(String[] args) {
        EightQueens eightQueens = new EightQueens();
        int[][] chess = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                chess[i][j] = 0;
            }
        }
        eightQueens.putQueen(chess, 0);
    }
}
