package Top;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-01
 */
public class P2_双指针 {


    public boolean isPalindrome(String s) {
        int n = s.length();
        char[] cs = s.toLowerCase().toCharArray();// ✅ 转小写
        int i = 0, j = n - 1;
        // ✅逻辑是：对小写字母进行对比
        while (i < j) {
            if (!Character.isLetterOrDigit(cs[i])) {
                i++;
                continue;
            }
            if (!Character.isLetterOrDigit(cs[j])) {
                j--;
                continue;
            }
            if (cs[i] != cs[j]) return false;
            i++;
            j--;
        }
        return true;
    }

    public boolean isSubsequence(String s, String t) {
        // 逻辑：判断 s 是否 t 子序列、fort 然后比对直到 s 的长度
        int n = s.length(), m = t.length(), p = 0;
        if (n == 0) return true; // ✅ 特判：空字符串是任何字符串的子序列
        if (n > m) return false; // 如果 s 比 t 还长，绝对不可能是子序列

        for (char c : t.toCharArray()) {
            if (s.charAt(p) == c) p++; // ❌如果 s 是空字符串 ""，n = s.length() 为 0。 在执行 s.charAt(p) 时
            if (p == n) return true;
        }
        return false;
    }

    public int[] twoSum(int[] numbers, int target) {
        int[] res = new int[2];
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            if (numbers[i] + numbers[j] == target) {
                return new int[]{i + 1, j + 1};//✅ 他需要的第几个
            } else if (numbers[i] + numbers[j] > target) {
                --j;
            } else {
                ++i;
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);// ✅
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n - 2; i++) { // ✅ 预留两个位置
            if (i > 0 && nums[i] == nums[i - 1] && i < nums.length - 2) continue;
            // ✅去重逻辑：(0,n-2) 进行，避免越界

            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int cur = nums[i] + nums[l] + nums[r];
                if (cur > 0) {
                    r--;
                } else if (cur < 0) {
                    l++;
                } else {
                    ans.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    // ✅ 去重逻辑：先移动下次 eg r-- 然后在去重逻辑 直到不同的
                    for (r--; l < r && nums[r] == nums[r + 1]; r--) ;
                    for (l++; l < r && nums[l] == nums[l - 1]; l++) ;
                }
            }
        }
        return ans;
    }

    public int maxArea(int[] height) {
        // ✅公式：底 * min高
        // 算法逻辑 每次移动比较小的那个、有可能变得比较大
        int res = Integer.MIN_VALUE, i = 0, j = height.length - 1;
        while (i < j) {
            // ❌ 宽度应该是 j - i 例子 i=0,j=1，这两个柱子之间的距离（底边的长度）是 1 、不是元素个数 才是 j-i+1
            // ❌ res = Math.max(res , (j-i+1) * Math.min(height[i],height[j]));
            res = Math.max(res, (j - i) * Math.min(height[i], height[j]));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return res;
    }
}
