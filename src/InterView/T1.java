package InterView;

import java.util.HashSet;
import java.util.Scanner;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-08
 */
public class T1 {
    public static void main(String[] args) {
        // 类似区间改变 - 差分算法
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[] diff = new int[n + 2];
        for (int i = 0; i < m; i++) {
            int l = scanner.nextInt();
            int r = scanner.nextInt();
            int d = scanner.nextInt();
            diff[l] += d;
            diff[r + 1] -= d;
        }
        // set 去重
        int[] nums = new int[n + 1]; // 还原
        HashSet<Integer> set = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            nums[i] = nums[i-1] + diff[i];
            set.add(nums[i]);
        }
        System.out.println(set.size());

    }
}

