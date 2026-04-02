package Top;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-02
 */
public class P3_滑动窗口 {


    public int minSubArrayLen(int target, int[] nums) {
        int l = 0, r = 0, len = nums.length;
        int winSum = 0;
        int ans = Integer.MAX_VALUE; // int ans = 0; ❌ 取消得 Integer.MAX_VALUE、不然全都 0
        while (r < len) {
            //add
            winSum += nums[r];
            r++;
            //remove
            while (winSum >= target) {
                ans = Math.min(ans, r - l);
                winSum -= nums[l]; // // ❌ 漏了 winSum -= nums[left];
                l++;
            }
        }
        // ❌ return ans; 如果不存在符合条件的子数组，返回 0
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    public int lengthOfLongestSubstring(String s) {
        int l = 0, r = 0, len = s.length();
        if (s.length() == 0) return 0;// ✅答案肯定没

        boolean[] exited = new boolean[128]; // ✅ s 由英文字母、数字、符号和空格组成
        int ans = Integer.MIN_VALUE;
        char[] cs = s.toCharArray();
        while (r < len) {
            //add
            char add = cs[r];
            r++;
            while (exited[add]) {// remove
                char remove = cs[l];
                exited[remove] = false;
                l++;
            }
            // ❌【缺失一步】：现在窗口里肯定没有 add 这个字符了，必须把它存进去！
            exited[add] = true;
            ans = Math.max(ans, r - l);
        }
        return ans;
    }


//    public String minWindow(String S, String t) {
//        // ✅t 是子串、去 s 找最小
//        int[] cntS = new int[128];
//        int[] cntT = new int[128];
//        for (char c : t.toCharArray()) cntT[c]++;
//
//        char[] s = S.toCharArray();
//        int left = 0, right = 0, len = S.length();
//        int minLen = Integer.MAX_VALUE, minLeft = 0;
//        while (right < len) {
//
//            cntS[s[right]]++; // 右端点字母移入子串
//            right++;
//
//            // while (Arrays.equals(cntS, cntT)) { ❌ 不能用 Arrays.equals(cntS, cntT)、可以更多
//            while (isCovered(cntS, cntT)) {
//                if (right - left < minLen) { // 找到更短的子串
//                    minLen = right - left; // 记录此时的左右端点
//                    minLeft = left;
//                }
//                cntS[s[left]]--; // 左端点字母移出子串
//                left++;
//            }
//        }
//
//        return minLen == Integer.MAX_VALUE ? "" : S.substring(minLeft, minLeft + minLen);
//    }

    private boolean isCovered(int[] cntS, int[] cntT) {
        for (int i = 0; i < 128; i++) {
            if (cntS[i] < cntT[i]) return false;
        }
        return true;
    }

    public String minWindow(String s, String t) {
        int l = 0 ;

    }


    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        int wordLen = words[0].length();
        int wordCount = words.length;
        int windowSize = wordLen * wordCount;

        // 1. 统计目标单词的频率
        Map<String, Integer> targetFreq = new HashMap<>();
        for (String w : words) {
            targetFreq.put(w, targetFreq.getOrDefault(w, 0) + 1);
        }

        // 2. 多起点滑动窗口（共 wordLen 个起点）
        for (int i = 0; i < wordLen; i++) {
            Map<String, Integer> windowFreq = new HashMap<>();
            int overLimit = 0; // 统计当前窗口内“数量超标”或“不该出现”的单词种类数

            for (int right = i + wordLen; right <= s.length(); right += wordLen) {
                // 【进】：新单词进入窗口 +wordLen是右侧的
                String wordIn = s.substring(right - wordLen, right);
                // 如果当前词频已经达到目标，再加这个词就会“超标”
                if (windowFreq.getOrDefault(wordIn, 0).equals(targetFreq.getOrDefault(wordIn, 0))) {
                    overLimit++;
                }
                windowFreq.put(wordIn, windowFreq.getOrDefault(wordIn, 0) + 1);

                // 【判断】：窗口是否已经达到预定长度
                int left = right - windowSize;
                if (left < i) continue; // 长度不足，继续加词

                // 如果没有单词超标，且窗口长度固定，则说明完美匹配
                if (overLimit == 0) {
                    result.add(left);
                }

                // 【出】：最左边的单词离开窗口
                String wordOut = s.substring(left, left + wordLen);
                // 如果该词在离开前是“超标”状态（比目标多1），离开后就恢复正常了
                if (windowFreq.get(wordOut).equals(targetFreq.getOrDefault(wordOut, 0) + 1)) {
                    overLimit--;
                }
                windowFreq.put(wordOut, windowFreq.get(wordOut) - 1);
            }
        }
        return result;
    }

}
