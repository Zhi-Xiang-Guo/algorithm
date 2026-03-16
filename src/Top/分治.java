package Top;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-08
 */
public class 分治 {


    public ListNode sortList(ListNode head) {
        // ✅ 没得拆分了
        if (head == null || head.next == null) return head;

        // 拆分两半 - 找重点
        // ❌ 注意：fast 从 head.next 开始，这样 slow 最终会停在左半部分的结尾
        // ListNode slow = head, fast = head;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode rightNode = slow.next;
        slow.next = null;

        // ✅ 拆分子链表
        ListNode left = sortList(head);
        ListNode right = sortList(rightNode);

        // 合并操作
        ListNode tmp = new ListNode(-1), p = p;
        while (left != null && right != null) {
            if (left.val >= right.val) {
                p.next = right;
                right = right.next;
            } else {
                p.next = left;
                left = left.next;
            }
            p = p.next;
        }
        // ❌ 漏了 如果 left 或 right 还有剩余节点，直接接在后面
        p.next = (left != null) ? left : right;

        return tmp.next;
    }

    public ListNode mergeKLists(ListNode[] lists) {

    }

    private Random rand = new Random();

    public int findKthLargest(int[] nums, int k) {
        // 定基准值
        int pivot = nums[rand.nextInt(nums.length)];
        // 数组过滤成三个流
        int[] bigger = Arrays.stream(nums).filter(n -> n > pivot).toArray();
        int[] equal = Arrays.stream(nums).filter(n -> n == pivot).toArray();
        int[] smaller = Arrays.stream(nums).filter(n -> n < pivot).toArray();

        // 从右到左第k个元素的位置，在bigger里
        // 解释：如果你要找全班前 3 名，而“高分组”(bigger)里正好有 5 个人。那前 3 名肯定就在这 5 个人里面
        if (k <= bigger.length) {
            return findKthLargest(bigger, k);
        }
        // 在smaller中
        // “高分组”(bigger)有 4 人 “相同组”(equal)有 2 人。前两组加起来才 6 个人，
        // 第 10 名肯定不在前两组，只能在“低分组”(smaller)里
        else if (k > (bigger.length + equal.length)) {
            return findKthLargest(smaller, k - bigger.length - equal.length);
        } else {
            return pivot;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        PriorityQueue<ListNode> q = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode list : lists) if (list != null) q.add(list);

        ListNode tummy = new ListNode(-1), p = tummy;
        while (!q.isEmpty()) {
            ListNode cur = q.poll();
            p.next = cur;
            if (cur.next != null) q.add(cur.next);
            p = p.next;
        }
        return tummy.next;

    }
}
