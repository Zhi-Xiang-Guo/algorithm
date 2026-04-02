package Top;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-05
 */
public class 区间 {

    public List<String> summaryRanges(int[] nums) {
        int n = nums.length;
        int i = 0;
        ArrayList<String> ans = new ArrayList<>();
        while (i < n) {
            int left = i;
            // while(left<n-1 && nums[left] == nums[left+1]) left++; // 最终在闭区间 r
            // ❌ while (i < n - 1 && nums[i] == nums[i + 1]) i++; // 最终在闭区间 r
            while (i < n - 1 && nums[i] == nums[i + 1] - 1) i++; // 最终在闭区间 r
            int right = i;
            // [l , r] -
            // ❌注意这里应该拼接 nums[left] 和 nums[right]，而不是索引 left 和 right
            if (left == right) {
                // ans.add("" + left);
                ans.add(String.valueOf(nums[left]));
            } else {
                ans.add(nums[left] + "->" + nums[right]);
            }
            i++;
        }
        return ans;
    }

    public int[][] merge(int[][] intervals) {
        // [a, b] 和 [c, d]
            // a<=d && b >=c
            // a<=c -> a<=d 无需判断了 就看 b>=c - b<c 即无相交
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        ArrayList<int[]> ans = new ArrayList<>();
        int[] last = intervals[0];
        ans.add(last);

        //  for (int[] cur : intervals) {
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];
            if (cur[0] > last[1]) {
                ans.add(cur);
                last = cur;
            } else {
                last[1] = Math.max(last[1],cur[1]);
            }
        }
        return ans.toArray(new int[ans.size()][2]);
    }

}
