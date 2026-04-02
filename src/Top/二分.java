package Top;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-09
 */
public class 二分 {

    public int searchInsert(int[] nums, int target) {
        int len = nums.length;
        int left = -1, right = len;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        // int left = 0 , right = matrix[0].length - 1;
        int row = 0, col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--; // 往左找
            } else {
                row++;
            }
        }
        return false;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        // 一维上二分 可以讲二维 to 一维
        // 但是这里可以有映射关系
        int n = matrix.length, m = matrix[0].length;
        int left = -1, right = m * n;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            // 寻找对应关系 、eg 第 5 个元素 是 (1,1)
            int cur = matrix[mid / m][mid % m];
            if (cur == target) {
                return true;
            } else if (cur > target) {
                // 往左找
                right = mid;
            } else {
                left = mid;
            }
        }
        return false;
    }

    public int findMin(int[] nums) {
        int left = -1, right = nums.length;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[nums.length - 1]) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return nums[right]; //
    }

    public int findPeakElement(int[] nums) {
        int left = -1, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) { // 上坡
                left = mid;
            } else {
                right = mid;
            }
        }
        return right; // 循环结束后 left == right 都可以
    }


    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int left = lowerBound(nums, target);
        if (left == n || nums[left] != target) {
            return new int[]{-1, -1};
        }
        // ❌ 注意 找 nums 的最右边呀 int end = lowerBound(nums, target);
        int end = lowerBound(nums, target + 1) - 1;
        // ✅ eg找 8 最右边 lowbound(9) - 1
        return new int[]{left, end};
    }

    private int lowerBound(int[] nums, int target) {
        int left = -1, right = nums.length; // 开区间 (left, right)
        while (left + 1 < right) { // 区间不为空
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }


    public String minWindow(String s, String t) {
    }
}
