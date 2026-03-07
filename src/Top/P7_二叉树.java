package Top;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-06
 */
public class P7_二叉树 {

//    public int maxDepth(TreeNode root) {
//        if (root == null) {
//            return 0;
//        }
//        int left = maxDepth(root.left);
//        int right = maxDepth(root.right);
//        return Math.max(left, right) + 1;
//    }

    int maxDepth = 0;

    public int maxDepth(TreeNode root) {
        // ❌ 当前有 1 个 dfs(root, 0);
        dfs(root, 1);
        return maxDepth;
    }

    private void dfs(TreeNode root, int deep) {
        if (root == null) return;
        maxDepth = Math.max(maxDepth, deep);
        dfs(root.left, deep + 1);
        dfs(root.right, deep + 1);
    }


    public boolean isSameTree(TreeNode p, TreeNode q) {
        // p q 值 和 左右等
        if (p == null || q == null) {
            return p == q;
        }

        return p.val == q.val
                && isSameTree(p.left, q.left)
                && isSameTree(p.right, q.right);
    }

    public TreeNode invertTree(TreeNode root) {
        // 左右反转
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    public boolean isSymmetric(TreeNode root) {
        return isSymmetricdfs(root.left, root.right)
    }

    private boolean isSymmetricdfs(TreeNode p, TreeNode q) {
        // p q 值 和 左右等
        if (p == null || q == null) {
            return p == q;
        }

        return p.val == q.val
                && isSameTree(p.left, q.right)
                && isSameTree(p.right, q.left);
    }

    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        LinkedList<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) { // 用固定板子比较好 int size = q.size(); for (int i = 0; i < size; i++) {
                Node cur = q.poll();
                if (size == 0) { // ✅ 最后一个节点 没有后续了
                    cur.next = null;
                } else {
                    cur.next = q.peek(); // 拼接下一个节点
                }

                if (cur.left != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }
        }
        return root;
    }


    public void flatten(TreeNode root) {
        flattenHelper(root);
    }

    private TreeNode flattenHelper(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = flattenHelper(root.left);
        TreeNode right = flattenHelper(root.right);
        // 先把左边 拼接 root 右边
        root.right = left;
        root.left = null;
        // 再把 右边 拼接到后面
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;
        return root;
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {

//        if (root.left == null && root.right == null) {
//            // ❌只处理了 null 节点： 假设一个节点只有左孩子，没有右孩子。 当递归调用
//            if (targetSum - root.val == 0) {
//                return true;
//            }
//            return false;
//        }
        if (root == null) {
            return false;
        }
        targetSum -= root.val;
        if (targetSum == 0) {
            return true;
        }
        return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
    }

    int ans = 0;

    public int sumNumbers(TreeNode root) {
        sumNumbersHelper(root, 0);
        return ans;
    }

    private void sumNumbersHelper(TreeNode root, int sum) {
        if (root == null) return;
        sum = sum * 10 + root.val;
        //  ans += sum;  ❌ 你目前是在每一个节点都把当前的 sum 加到了 ans 里，只累加从根到“叶子节点”路径
        // 2. 关键：判断是否是叶子节点
        if (root.left == null && root.right == null) {
            ans += sum; // 只有到头了才加到总和里
            return;
        }
        sumNumbersHelper(root.left, sum);
        sumNumbersHelper(root.right, sum);
    }

    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode root, int prevSum) {
        if (root == null) return 0;
        int currentSum = prevSum * 10 + root.val;
        // 如果是叶子节点，直接返回当前路径值
        if (root.left == null && root.right == null) {
            return currentSum;
        }
        // ✅ 先假设在顶部，即左 右孩子给我答案
        return dfs(root.left, currentSum) + dfs(root.right, currentSum);
    }


    private int ans;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int dfs(TreeNode node) {
        if (node == null) {
            return -1; // 对于叶子来说，链长就是 -1+1=0
        }
        int lLen = dfs(node.left) + 1; // 左子树最大链长+1
        int rLen = dfs(node.right) + 1; // 右子树最大链长+1
        ans = Math.max(ans, lLen + rLen); // 两条链拼成路径
        return Math.max(lLen, rLen); // 当前子树最大链长
    }

    private int ans = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;
        int left = dfs(node.left);
        int right = dfs(node.right);
        int curSum = node.val + left + right;
        ans = Math.max(ans, curSum);
        return Math.max(0, node.val + Math.max(left, right));
    }

    public int countNodes(TreeNode root) {
        TreeNode l = root, r = root;
        // 去拿左右的 子树高  对比
        int levelL = 0, levelR = 0;
        for (; l != null; levelL++) l = l.left;
        for (; l != null; levelR++) r = r.right;
        return levelL == levelR
                ? (int) Math.pow(2, levelL) - 1  // 2^3 -1
                : 1 + countNodes(root.left) + countNodes(root.right); // 递归下去 按照之前那种
    }

    public int countNodes(TreeNode root) {
        TreeNode l = root, r = root;
        int levelLeft = 0, levelRight = 0;
        //✅ 需要计数  while (l != null) l = l.left , levelLeft++;
        for (; l != null; levelLeft++) l = l.left;
        for (; r != null; levelRight++) r = r.right;
        return levelLeft == levelRight ?
                (int) Math.pow(2, levelLeft) - 1
                : 1 + countNodes(root.left) + countNodes(root.right);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        if (root == null) return null;
//        if (p.val == root.val) return p;
//        if (q.val == root.val) return q; 可以优化分支 ✅

        if (root == null || root == p || root == q) { //❌ 忘记补当前是 p q 可以返回
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }


    public List<Integer> rightSideView(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        rightSideViewHelper(root, 0, ans);
        return ans;
    }

    private void rightSideViewHelper(TreeNode root, int depth, ArrayList<Integer> ans) {
        if (root == null) return;
        if (depth == ans.size()) ans.add(root.val);
        rightSideViewHelper(root.right, depth + 1, ans);
        rightSideViewHelper(root.left, depth + 1, ans);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        List<List<Integer>> ans = new ArrayList<>();
        if (root != null) q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            ArrayList<Integer> curLevel = new ArrayList<>();
            while (size-- > 0) {
                TreeNode cur = q.poll();
                curLevel.add(cur.val);
                if (cur.left != null) {
                    q.add(cur.left);
                }
                if (cur.right != null) {
                    q.add(cur.right);
                }
            }
            ans.add(curLevel);
        }
        return ans;
    }

    public List<Double> averageOfLevels(TreeNode root) {
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        List<Double> ans = new ArrayList<>();
        if (root != null) q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            double curSum = 0;
            // while (size-- > 0) {
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                curSum += cur.val;
                if (cur.left != null) {
                    q.add(cur.left);
                }
                if (cur.right != null) {
                    q.add(cur.right);
                }
            }
            ans.add(curSum / size);
        }
        return ans;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        List<List<Integer>> ans = new ArrayList<>();
        if (root != null) q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            ArrayList<Integer> curLevel = new ArrayList<>();
            while (size-- > 0) {
                TreeNode cur = q.poll();
                curLevel.add(cur.val);
                if (cur.left != null) {
                    q.add(cur.left);
                }
                if (cur.right != null) {
                    q.add(cur.right);
                }
            }
            if (ans.size() % 2 != 0) Collections.reverse(curLevel); // ✅ 奇数
            ans.add(curLevel);
        }
        return ans;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Deque<TreeNode> d = new ArrayDeque<>();
        while (root != null || !d.isEmpty()) {
            // 步骤 1
            while (root != null) {
                d.addLast(root);
                root = root.left;
            }

            // 步骤 2
            root = d.pollLast();
            ans.add(root.val);
            // 步骤 3
            root = root.right;
        }
        return ans;
    }

    int ans = Integer.MAX_VALUE;
    private int pre = Integer.MIN_VALUE / 2; // 防止减法溢出

    public int getMinimumDifference(TreeNode root) {
        // 思路就是：比较中序 遍历下 、相邻的 abs
        getMinimumDifferenceHelper(root);
        return ans;
    }

    private void getMinimumDifferenceHelper(TreeNode root) {
        if (root == null) return;
        getMinimumDifferenceHelper(root.left);
        ans = Math.min(ans, Math.abs(root.val - pre));
        // ❌ 漏了
        pre = root.val;
        getMinimumDifferenceHelper(root.right);
    }

//    private long pre = Long.MIN_VALUE; // 有
//    public boolean isValidBST(TreeNode root) {
//        // ❌ if (root == null) return false;
//        // 1. 基准情况：空树是合法的 BST
//        if (root == null) return true;
//
//        boolean left = isValidBST(root.left);
//        if (!left) return false;
//
//        // 中序位置 root.val > pre 才对
//        if (root.val <= pre) return false;
//        pre = root.val;
//        boolean right = isValidBST(root.right); // 上面 left 可以了 其实就取决于 right
//        return right;
//    }

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode root, long minValue, long maxValue) {
        if (root == null) return true;
        //check cur  then
        return root.val > minValue && root.val < maxValue
                && isValidBST(root.left,minValue,root.val)
                && isValidBST(root.right,root.val,maxValue);

    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}