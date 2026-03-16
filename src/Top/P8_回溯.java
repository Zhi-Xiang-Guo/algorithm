package Top;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-07
 */
public class P8_回溯 {
    public List<String> letterCombinations(String digits) {
        String[] mapping = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ArrayList<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs(0, digits, mapping, sb, ans);
        return ans;
    }

    private void dfs(int idx, String digits, String[] mapping, StringBuilder sb, ArrayList<String> ans) {
        if (idx == digits.length()) {
            ans.add(sb.toString());
            return;
        }

        // choices
        char index = digits.charAt(idx);
        for (char c : mapping[index - '0'].toCharArray()) {
            sb.append(c);
            dfs(idx + 1, digits, mapping, sb, ans);
            sb.deleteCharAt(sb.length() - 1);
        }

    }

    public List<List<Integer>> combine(int n, int k) {
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        combineHelper(1, n, k, path, ans);
    }

    private void combineHelper(int begin, int n, int k, List<Integer> path, List<List<Integer>> ans) {

        for (int i = begin; i <= n; i++) {
            path.add(i);
            combineHelper(begin+1); //✅ 注意
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        boolean[] used = new boolean[6];// 标识 nums 下标值 使用没
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        dfs(nums, path, ans,used);
        return ans;
    }

    private void dfs(int[] nums, List<Integer> path, List<List<Integer>> ans, boolean[] used) {
        if (nums.length == path.size()) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i])continue;
            used[i] =true;
            path.add(nums[i]);
            dfs(nums,path,ans,used);
            used[i] =false;
            // ❌path.remove(nums[i]);
            path.remove(path.size()-1);
        }
    }



    List<List<String>>  res = new ArrayList<>();
    public List<List<String>> solveNQueens(int n) {
        char[][]  map = new char[n][n];
        //初始化棋盘
        for(int i = 0 ; i < n ; i++){
            for(int j = 0 ; j < n ; j++){
                map[i][j] = '.';
            }
        }
        backTrack(map,0,n);
        return res;
    }

    void backTrack(char[][]  map, int row , int n){
        if(row == n){
            res.add(construct(map,n));
            return;
        }

        for(int col = 0 ; col < n ; col++){
            if(isValid(map,row,col,n)){
                map[row][col] = 'Q';
                backTrack(map,row + 1,n);
                map[row][col] = '.';
            }
        }

    }

    //判断是否存在 Q 存在返回flase
    boolean isValid(char[][] map,int row,int col,int n){
        //因为是从上往下放，所以只需要判断,往上的上跟左斜右鞋是否有Q
        //上列是否存在Q
        for(int i = 0 ; i < row ; i++){
            if(map[i][col] == 'Q'){
                return false;
            }
        }
        //左斜 \
        for(int i = row - 1, j = col -1 ; i >= 0 && j >=0 ; i-- ,j--){
            if(map[i][j] == 'Q'){
                return false;
            }
        }
        //右斜 /
        for(int i = row - 1 , j = col + 1; i>=0 && j<n ; i--,j++){
            if(map[i][j] == 'Q'){
                return false;
            }
        }
        return true;
    }

    private List<String> construct(char[][] board) {
        List<String> path = new ArrayList<>();
        for (char[] row : board) path.add(new String(row)); // 直接转换
        return path;
    }

}
