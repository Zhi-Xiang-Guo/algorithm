package Top;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-10
 */
public class DP {
    public int climbStairs(int n) {
        // i  = i-1 + i-2 、
        // ✅ 这里不做移位 知道 0 1 答案 2 开始即可
        int[] f = new int[n + 1];
        f[0] = f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }


//    public int rob(int[] nums) {
//        // i = i-1 or i-2 + num[i]
//        // -1 or -2 情况
//        int n = nums.length;
//        int[] f = new int[n + 2];
//        for (int i = 0; i < n; i++) {
//            f[i+2] = Math.max(f[i+1],f[i]+nums[i]);
//        }
//        return f[n+1];
//    }

    public int rob(int[] nums) {
        // 只依赖 上 or 上上
        // i = i-1 or i-2 + num[i]
        int n = nums.length;
        int pre = 0;
        int prePre = 0;
        for (int i = 0; i < n; i++) {
            // cur = pre prePre
            // then cur 变 pre
            int cur = Math.max(pre, prePre + nums[i]);
            prePre = pre;
            pre = cur;
        }
        return pre;
    }


//    public boolean wordBreak(String s, List<String> wordDict) {
//        int n = s.length();
//        boolean[] f = new boolean[n + 1];
//        f[0] = true; // 空串 base
//        // i - f[i-w.len] && equals
//        // 往上推 ->
//        for (int i = 0; i < n; i++) {
//            for (String word : wordDict) {
//                // if (f[i] && i + word.length() <= n && s.substring(i, word.length()).equals (word)) {
//                if (f[i] && i + word.length() <= n && s.substring(i, i + word.length()).equals(word)) {
//                    // ✅ i + word.length() <= n 避免 sub 越界
//                    // ✅ i + word.length()
//                    f[i + word.length()] = true;
//                }
//            }
//        }
//        return f[n];
//    }

    public boolean wordBreak(String s, List<String> wordDict) {
        int maxLen = 0, minLen = Integer.MAX_VALUE;
        for (String word : wordDict) {
            maxLen = Math.max(maxLen, word.length());
            minLen = Math.min(minLen, word.length());  // 修正这里
        }
        Set<String> words = new HashSet<>(wordDict);

        int n = s.length();
        boolean[] f = new boolean[n + 1];
        f[0] = true;
        for (int i = 0; i < n; i++) {
            if (!f[i]) continue;  // 如果当前位置不可达，跳过
            // 往上递推 、✅ 只检查从 minLen 到 maxLen 的长度范围
            for (int len = minLen; len <= maxLen && i + len <= n; len++) {
                String sub = s.substring(i, i + len);
                if (words.contains(sub)) {
                    f[i + len] = true;
                }
            }
        }
        return f[n];
    }


    public int lengthOfLIS(int[] nums) {
        // di - di or dj + 1
        int n = nums.length;
        int[] f = new int[n];
        Arrays.fill(f, 1);
        int maxLen = 1;  // 记录最大值
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
                //❌最好别放着 f[i]更新完 在下面 maxLen = Math.max(f[i],maxLen);
            }
            maxLen = Math.max(f[i], maxLen);
        }
        return maxLen;
    }

    public int maxSubArray(int[] nums) {
        int n = nums.length;

    }

    // 多维 DP
    public int minDistance(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[][] dp = new int[n + 1][m + 1]; // 空串情况考虑
        // 初始化地基：其中一个为空串，要变成另一个，只能不停地插入
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // if (text1.charAt(i) == text2.charAt(j)) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1],//替换
                            dp[i - 1][j],//删 w1
                            dp[i][j - 1]//添加
                    ) + 1;
                }
            }
        }
        return dp[n][m];
    }


    private int min(int i, int i1, int i2) {
        return Math.min(i, Math.min(i1, i2));
    }


    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[] pre = new int[n + 1];  // 上一行
        int[] cur = new int[n + 1];  // 当前行

        // 初始化 pre（第一行）
        for (int j = 0; j <= n; j++) pre[j] = j;

        for (int i = 1; i <= m; i++) {
            cur[0] = i;  // 第一列
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    cur[j] = pre[j - 1];  // 左上角明确是 pre[j-1]
                } else {
                    cur[j] = Math.min(pre[j - 1], Math.min(pre[j], cur[j - 1])) + 1;
                }
            }
            // 交换
            int[] temp = pre;
            pre = cur;
            cur = temp;
        }
        return pre[n];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] f = new int[n][n];
        f[0][0] = triangle.get(0).get(0);

        int min = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i + 1; j++) {
                f[i][j] = Math.min(f[i-1][j], f[i + 1][j + 1]) + triangle.get(i).get(j);
                // triangle.get(i).get(j) 当前元素
                // 逻辑就是 上到下 有
            }
        }
        return f[0][0];

    }


    // 背包问题

    //    public boolean canPartition(int[] nums) {
//        int sum = 0;
//        for (int num : nums) sum += num;
//        if (sum % 2 != 0) return false; // 不可能平均两半
//        int target = sum / 2;
//        int n = nums.length;
//        boolean[][] f = new boolean[n + 1][target + 1];
//        f[0][0] = true;
//        for (int i = 1; i < n + 1; i++) { // nums[i]
//            for (int j = 0; j < target + 1; j++) { // weight
//                //❌ if (nums[i] > j) {
//                if (nums[i - 1] > j) {
//                    f[i][j] = f[i - 1][j];
//                } else {
//                    //❌ f[i][j] = f[i - 1][j] || f[i - 1][j - nums[i]];
//                    f[i][j] = f[i - 1][j] || f[i - 1][j - nums[i - 1]];
//                }
//            }
//        }
//        return f[n][target];
//    }
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false; // 不可能平均两半
        int target = sum / 2;
        int n = nums.length;
        boolean[] f = new boolean[target + 1];
        f[0] = true;
        // 解释  f[i][j] = f[i - 1][j] || f[i - 1][j - nums[i - 1]] 只依赖上一行
        for (int i = 1; i < n + 1; i++) { // nums[i]
            // for (int j = target + 1; j >=nums[i] ; j++) { // weight
            // for (int j = target + 1; j >= nums[i - 1]; j--) { // weight
            for (int j = target; j >= nums[i - 1]; j--) { // weight
                f[j] = f[j] || f[j - nums[i - 1]];
            }
        }
        return f[target];
    }

//    public int findTargetSumWays(int[] nums, int target) {
//        int sum = 0;
//        for (int num : nums) sum += num;
//        // ✅ 应该添加：问题在于 target 的计算可能不是整数，也没有检查 (sum - target) 是否为偶数
//        if ((sum - target) < 0 || (sum - target) % 2 != 0) {
//            return 0;
//        }
//        target = (sum - target) / 2;
//        int n = nums.length;
//
//        int[][] f = new int[n + 1][target + 1];
//        f[0][0] = 1;
//        for (int i = 1; i <= n; i++) {
//            for (int j = 0; j <= target; j++) {
//                if (nums[i - 1] > j) {
//                    f[i][j] = f[i - 1][j];
//                } else {
//                    f[i][j] = f[i - 1][j] + f[i - 1][j - nums[i-1]];
//                }
//            }
//
//        }
//        return f[n][target];
//    }

    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) sum += num;
        // 应该添加：
        target = (sum - Math.abs(target)) / 2;
        int n = nums.length;

        int[] f = new int[target + 1];
        f[0] = 1;
        for (int num : nums) {
            for (int j = target; j >= num; j--) {
                f[j] = f[j] + f[j - num];
            }
        }
        return f[target];
    }

    // 完全背包

    public int coinChange(int[] coins, int amount) {
        // i c - i-1 c or i-1 c-coin[i] + 1 , use min
        int n = coins.length;
        int V = amount;
        int[][] dp = new int[n + 1][V + 1];
        // ✅ 初始化：除了dp[0][0]=0，其他dp[0][j]设为无穷大
        for (int j = 1; j <= V; j++) {
            dp[0][j] = Integer.MAX_VALUE / 2; // 减1防止后面+1溢出
        }
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= V; j++) {
                if (j < coins[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // ❌dp[i][j - coins[i]] 应该是 -1
                    // ❌dp[i][j - coins[i - 1]]) + 1;  选 or 不选 是选才会加硬币数量
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - coins[i - 1]] + 1);
                }
            }
        }
        return dp[n][V] == Integer.MAX_VALUE / 2 ? -1 : dp[n][V];
    }

    public int change(int amount, int[] coins) {
        int n = coins.length;
        int V = amount;
        int[][] dp = new int[n + 1][V + 1];
        dp[0][0] = 1; // 方案数 ✅

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= V; j++) {
                if (j < coins[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                }
            }
        }
        return dp[n][V];
    }

    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) { // 遍历顺序
            for (int num : nums) {
                if (i >= num) {
                    dp[i] += dp[i - num];
                }
            }
        }
        return dp[target];
    }

}
