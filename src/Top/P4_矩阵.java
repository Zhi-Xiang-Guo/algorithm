package Top;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-03
 */
public class P4_矩阵 {


    public boolean isValidSudoku(char[][] board) {
        // ✅ eg row 9行、单行有 10个数、二维数组
        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] box = new boolean[9][10];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.') continue; // 空格不用检查
                int x = board[i][j] - '0';
                // 九宫格映射 ，9个格子 、先确定是第 x 行 y 列
                // newRow = x / 3 * 3 、 newCol = y / 3
                // ❌if (row[i][x] || row[j][x] || row[(i / 3) * 3 + j / 3][x]) return false;
                if (row[i][x] || col[j][x] || box[(i / 3) * 3 + j / 3][x]) return false;
                row[i][x] = col[j][x] = box[(i / 3) * 3 + j / 3][x] = true;
            }
        }
        return true;
    }

//    public List<Integer> spiralOrder(int[][] matrix) {
//        int l = 0, r = matrix[0].length - 1, top = 0, bottom = matrix.length - 1;
//        ArrayList<Integer> ans = new ArrayList<>();
//        while (true) {
//            for (int i = l; i <= r; i++) ans.add(matrix[top][i]);
//            // ❌ 应该往下缩小的 if (--top < bottom) break;
//            if (++top < bottom) break;
//
//            for (int i = top; i <= bottom; i++) ans.add(matrix[i][r]);
//            // if (--r > l) break;
//            if (--r < l) break;
//
//            for (int i = r; i >= l; i--) ans.add(matrix[bottom][i]);
//            // ❌ 向上收缩，所以是 bottom--if (++bottom > top) break;
//            // ❌if (--bottom > top) break;
//            if (--bottom < top) break;
//
//            // ❌从下往上 (遍历 left 列) for (int i = bottom; i <= top; i++) ans.add(matrix[i][l]);
//            for (int i = bottom; i >= top; i--) ans.add(matrix[i][l]);
//            if (++l > r) break; // 左边界右移
//        }
//        return ans;
//    }

    public List<Integer> spiralOrder(int[][] matrix) {
        // 1. 判空
        if (matrix == null || matrix.length == 0) return new ArrayList<>();

        // 2. 初始化边界
        int l = 0, r = matrix[0].length - 1;
        int t = 0, b = matrix.length - 1;
        List<Integer> ans = new ArrayList<>();

        while (true) {
            // 从左到右
            for (int i = l; i <= r; i++) ans.add(matrix[t][i]);
            if (++t > b) break; // 顶边界下移，如果 顶 > 底，说明全遍历完了

            // 从上到下
            for (int i = t; i <= b; i++) ans.add(matrix[i][r]);
            if (--r < l) break; // 右边界左移，如果 右 < 左，结束

            // 从右到左
            for (int i = r; i >= l; i--) ans.add(matrix[b][i]);
            if (--b < t) break; // 底边界上移，如果 底 < 顶，结束

            // 从下到上
            for (int i = b; i >= t; i--) ans.add(matrix[i][l]);
            if (++l > r) break; // 左边界右移，如果 左 > 右，结束
        }
        return ans;
    }


    public void rotate(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        //分析： 第 i 行去了 col - i -1 列 、 第 j 列去 i 行
        //
        // ✅ 先处理第 j 列去 i 行 -  转置：把矩阵按照主对角线翻转，位于 (i,j) 的元素去到 (j,i)
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        for (int i = 0; i < row; i++) {
            // for (int j = 0; j <= col / 2; j++) {
            for (int j = 0; j < col / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][col - 1 - j];
                matrix[i][col - 1 - j] = tmp;
            }
        }
    }

    public void gameOfLife(int[][] board) {
        int row = board.length, col = board[0].length;
        int[][] move = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {-1, 1}, {1, 1}, {1, -1}, {-1, -1}};

        // 第一遍遍历：标记复合状态
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int liveNeighbors = 0;
                for (int[] m : move) {
                    int x = i + m[0], y = j + m[1];
                    if (x >= 0 && x < row && y >= 0 && y < col && Math.abs(board[x][y]) == 1) {
                        // ✅【关键点】只要绝对值是 1，说明原来是活的 因为 1 是保持活，-1 是从活变死，它们原本都是 1
                        liveNeighbors++;
                    }
                }
                // 规则 1 & 3：活细胞死亡
                if (board[i][j] == 1 && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    board[i][j] = -1; // 标记：活 -> 死
                }
                // 规则 4：死细胞复活
                if (board[i][j] == 0 && liveNeighbors == 3) {
                    board[i][j] = 2;  // 标记：死 -> 活
                }
            }
        }

        // 第二遍遍历：恢复为最终状态 (0 和 1)
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == -1) board[i][j] = 0;
                if (board[i][j] == 2) board[i][j] = 1;
            }
        }
    }


}
