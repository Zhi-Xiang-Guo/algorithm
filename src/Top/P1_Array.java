package Top;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yourname guozhixiang03
 * Created on 2026-02-27
 */
public class P1_Array {


    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 逆向：
        // nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
        int p = m + n - 1, p1 = m - 1, p2 = n - 1;
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] >= nums2[p2]) {
                nums1[p--] = nums1[p1--];
            } else {
                nums1[p--] = nums2[p2--];
            }
        }
//        while (p1 >= 0) nums1[p--] = nums1[p1--]; ✅只需要把 数组 2 的移动即可
        while (p2 >= 0) nums1[p--] = nums2[p2--];
    }

    public int removeElement(int[] nums, int val) {
        // 维护 0～slow 非 val 即可
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) nums[slow++] = nums[fast];
        }
        return slow;// ✅ slow 是长度了
    }

    public int removeDuplicates(int[] nums) {
        // ✅ i-1 i-2 前两个，初始化 2 先
        int slow = 2;
        for (int fast = 2; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - 2]) nums[slow++] = nums[fast];
        }
        return slow;
    }

    public int majorityElement(int[] nums) {
        // ✅ 每个人给自己投 、如果 vote = 0 换人 变自己
        int x = 0, votes = 0;
        for (int num : nums) {
            if (votes == 0) x = num;
            votes +=
                    x == num ? 1 : -1;
        }
        return x;
    }

    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        // ❌ k-1 不是左边区间的 reverse(nums, k-1, n - 1);
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] nums, int i, int j) {
        while (i < j) { // 首位交换而已
            int temp = nums[i];
            nums[i++] = nums[j];
            nums[j--] = temp;
        }
    }

    public int maxProfit(int[] prices) {
        // 思路：记录 maxProfit、minPrice - ✅minPrice取最小 那初始化最大值即可 0不行
        int minPrice = Integer.MAX_VALUE, maxProfit = 0;
        for (int price : prices) {
            maxProfit = Math.max(maxProfit, price - minPrice);// ✅先算 prof 、因为是min 是看之前的
            minPrice = Math.min(minPrice, price);
        }
        return maxProfit;
    }

    public boolean canJump(int[] nums) {
        // 思路：
        int fastest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > fastest) return false;
            fastest = Math.max(fastest, i + nums[i]);
        }
        return true;
    }

    public int jump(int[] nums) {
        int count = 0, curR = 0, nextR = 0;
        for (int i = 0; i < nums.length - 1; i++) { // ✅ 是 n-2 即可
            nextR = Math.max(nextR, i + nums[i]); // ✅在前面 避免漏当前跳的信息 、eg 刚开始就得跳了

            if (i == curR) { // ✅ 到达边界了
                curR = nextR;
                count++;
            }
        }
        return count;
    }

    public int hIndex(int[] citations) {
        // 引用次数做桶下标 这样可以知道 被引用 h 篇的文章有 counts[h] 个
        int n = citations.length;
        int[] counts = new int[n + 1];
        for (int citation : citations) {
            counts[Math.min(citation, n)]++;
        }

        int count = 0;
        // ✅ 从大往下枚举 n 、 h个 和 引用 >=h
        for (int h = n; h >= 0; h--) {
            count += counts[h];
            if (count >= h) return h;
        }
        return -1;
    }

    public int hIndex2(int[] citations) {
        int n = citations.length;
        int left = -1, right = n + 1;// ❌right = n 可能为 n、 left = -1 会报错

        // 二分逻辑就是 看 mid 点 n-mid 就是论文数
        // ✅那么就是看citations[n-mid] >= mid ?
        while (left + 1 < right) {
            int h = left + (right - left) / 2;
            if (citations[n - h] >= h) {
                left = h;
            } else {
                right = h;
            }
        }
        return left;
    }

    public int[] productExceptSelf(int[] nums) {
        // 逻辑就是：前后缀处理 除了 cur 外的
        int len = nums.length;
        int[] pre = new int[len], suf = new int[len];
        pre[0] = 1;
        suf[len - 1] = 1;
        // 初始边界 pre0 0前面没数据 弄成 1 即可
        for (int i = 1; i < len; i++) pre[i] = pre[i - 1] * nums[i - 1];
        for (int i = len - 2; i >= 0; i--) suf[i] = suf[i + 1] * nums[i + 1];

        int[] ans = new int[len];
        for (int i = 0; i < len; i++) ans[i] = pre[i] * suf[i];
        return ans;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int ans = 0, minGas = 0, gasLeft = 0;
        for (int i = 0; i < gas.length; i++) {
            gasLeft += gas[i] - cost[i]; // ✅ 从 i 到 i+1了
            if (gasLeft < minGas) {// 更新最小油量
                minGas = gasLeft;
                ans = i + 1; // ✅注意 s 减去 cost[i] 之后，汽车在 i+1 而不是 i
            }
        }
        // ✅ 判断全局油量
        return gasLeft < 0 ? -1 : ans;
    }

    public int candy(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);// 初始化：每个孩子至少1个糖果

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) candies[i] = candies[i - 1] + 1;
        }

        // ❌ 0 for (int i = n; i > 0; i--) {
        // 和右边比较 所以得初始化 n-2 索引越界考虑、而且漏掉了索引
        for (int i = n - 2; i >= 0; i--) {
            // ❌ if (ratings[i] > ratings[i + 1])
            // 取当前值和新计算值的最大值，以满足两边规则 candies[i] = candies[i + 1] + 1;
            if (ratings[i] > ratings[i + 1]) candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        }
        int count = 0;
        for (int candy : candies) count += candy;
        return count;
    }

    public int trap(int[] height) {
        // ✅ 算法逻辑：积水S = 当前 H 和 左与右的最大值 中 min 做差
        // 避免 On 获取 max 预处理先
        int n = height.length;
        int[] leftMost = new int[n], rightMost = new int[n];
        // ✅注意 0 n-1 不积水
        for (int i = 1; i < leftMost.length - 1; i++)
            leftMost[i] = Math.max(leftMost[i - 1], height[i - 1]);
        for (int i = rightMost.length - 2; i >= 1; i--)
            rightMost[i] = Math.max(rightMost[i + 1], height[i + 1]);

        int ans = 0;
        for (int i = 1; i < height.length - 1; i++) {
            int s = Math.min(leftMost[i], rightMost[i]) - height[i];
            ans += Math.max(s, 0);
        }
        return ans;
    }

    public int romanToInt(String S) {
        int ans = 0;
        char[] cs = S.toCharArray();
        for (int i = 0; i < cs.length - 1; i++) { // n-2 停止
            int x = getValue(cs[i]);
            int y = getValue(cs[i + 1]);
            ans += x < y ? -x : x;
        }
        return ans + getValue(cs[cs.length - 1]);
    }

    public static int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }


    private static final String[][] R = new String[][]{
            {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}, // 个位
            {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}, // 十位
            {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}, // 百位
            {"", "M", "MM", "MMM"}, // 千位
    };

    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        // ✅注意是高位 前先加入
        sb.append(R[3][num / 1000 % 10]);
        sb.append(R[2][num / 100 % 10]);
        sb.append(R[1][num / 10 % 10]);
        sb.append(R[0][num % 10]);
        return sb.toString();
    }

    public int lengthOfLastWord(String s) {
        // 输入：s = "   fly me   to   the moon  "

        // 先找到第一个非空即 n 、然后再找到空 停止
        int len = s.length();
        int right = len - 1; //  int start = len ❌ 应该是 n -1
        // ❌其实就是条件 移动指针、这样写 break 没有 不就死循环了吗
        // while (start >= 0) {
        //            if (s.charAt(start) == ' ') start--;
        //  }
        while (right >= 0 && s.charAt(right) == ' ') right--;

        // 直到是空格
        int left = right;
        while (left >= 0 && s.charAt(left) != ' ') left--;
        // return end - start;
        return right - left;
    }


    public String longestCommonPrefix(String[] strs) {
        // strs = ["flower","flow","flight"]
        String str = strs[0];
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length; i++) {

            // check other 拿出 strs
            for (int j = 1; j < strs.length; j++) {
                String cur = strs[j];
                // ❌ 还有越界 case 记得判断 if (cur.charAt(i) != cs[i]) {
                if (i == cur.length() || cur.charAt(i) != cs[i]) {
                    return str.substring(0, i);// i是开区间 没满足
                }
            }
        }
        return str;
    }

//    public String reverseWords(String s) {
//       // 前后空格
//        String[] strs = s.trim().split(" ");
//        ArrayList<String> ans = new ArrayList<>();
//        for (String str : strs) {
//            if (str.equals(""))continue;
//            // 不对 if (str.equals(" "))continue; Java 会多两个空串
//            ans.add(str);
//        }
//        Collections.reverse(ans); // ✅ 忘记反转了
//        return String.join(" ", ans);
//    }

    public String reverseWords(String s) {
        s = s.trim();                                    // 删除首尾空格
        int j = s.length() - 1, i = j;
        StringBuilder res = new StringBuilder();
        while (i >= 0) {
            while (i >= 0 && s.charAt(i) != ' ') i--;     // 搜索首个空格
            res.append(s.substring(i + 1, j + 1) + " "); // 添加单词

            while (i >= 0 && s.charAt(i) == ' ') i--;     // 跳过单词间空格
            j = i;                                       // j 指向下个单词的尾字符
        }
        return res.toString().trim();                    // 转化为字符串并返回
    }

    public String convert(String s, int numRows) {
        int n = s.length();
        if (numRows == 1 || numRows >= n) return s;

        String[] rows = new String[numRows]; // ✅ 用 String 做拼接的
        Arrays.fill(rows, "");// 注意初始化

        boolean down = false;// ✅ 控制 curR 上下走、 初始化 false 因为顶部会反转下
        int curRow = 0;// ✅ 控制 当前是填哪个 rows 数组
        char[] cs = s.toCharArray();
        for (char c : s.toCharArray()) {
            rows[curRow] += c;
            if (curRow == 0 || curRow == numRows - 1) down = !down;
            curRow += down ? 1 : -1;
        }

        String ans = "";
        for (String row : rows) ans += row;
        return ans;
    }

    public int strStr(String haystack, String needle) {
        //start ~ start + len有没有
        return haystack.indexOf(needle);
    }

    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        List<String> curLinePath = new ArrayList<>(); // 当前行的单词
        int currentWordsLength = 0; // 当前行所有单词的字符长度之和
        for (String word : words) {
            // ✅：超过 maxLen 即 单词总长 + 待加入单词 + 最少空隙 、解释最少空隙=curLinePath size 一个空格
            if (currentWordsLength + word.length() + curLinePath.size() > maxWidth) {
                result.add(foramtLine(curLinePath, currentWordsLength, maxWidth));
                // ✅ 清空 已经 flush 了
                curLinePath.clear();
                currentWordsLength = 0;
            }
            // ✅ 不上刚刚多个 word ，继续下次
            curLinePath.add(word);
            currentWordsLength += word.length();
        }

        // 最后一行处理
        result.add(foramtLineLast(curLinePath, currentWordsLength, maxWidth));
        return result;
    }

    private String foramtLineLast(List<String> curLinePath, int currentWordsLength, int maxWidth) {
        // ✅ 最后一行逻辑是：单词间只加一个空格
        StringBuilder sb = new StringBuilder();
        int allGapLen = maxWidth - currentWordsLength;
        int gapCount = curLinePath.size() - 1;
        int moreGap = allGapLen - gapCount;
        for (int i = 0; i < curLinePath.size(); i++) {
            sb.append(curLinePath.get(i));
            if (i < gapCount) sb.append(" "); // 单词间只加一个空格
        }
        sb.append(" ".repeat(moreGap)); // ✅ 最后补上
        return sb.toString();
    }

    private String foramtLine(List<String> curLinePath, int currentWordsLength, int maxWidth) {
        // ✅：非最后一行 且 只有一个单词 ：左对齐逻辑 —— 单词 加空格补全完到 MaxLen
        if (curLinePath.size() == 1)
            return curLinePath.get(0) + " ".repeat(maxWidth - currentWordsLength);

        StringBuilder sb = new StringBuilder();
        // ✅ 空格分配逻辑：计算平均 和 多的空格 - 余数、 按照左多来逐个分配
        int allGapLen = maxWidth - currentWordsLength;
        int gapCount = curLinePath.size() - 1; // 总共空格数
        int baseSpacesPerGap = allGapLen / gapCount;
        int extraSpacesCount = allGapLen % gapCount;

        for (int i = 0; i < curLinePath.size(); i++) {
            sb.append(curLinePath.get(i));
            if (i < gapCount) {
                // ✅如果不是最后一个单词，则需要添加间隙空格
                int repeat = baseSpacesPerGap + ((i < extraSpacesCount) ? 1 : 0);
                sb.append(" ".repeat(repeat));
            }
        }
        return sb.toString();
    }


}
