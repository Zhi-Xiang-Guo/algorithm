package Top;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yourname guozhixiang03
 * Created on 2026-03-05
 */
public class P6_链表 {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            // if (slow == head) return true;
            if (slow == fast) return true;

        }
        return false;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1, p2 = l2;
        ListNode tmp = new ListNode(-1), p = tmp;
        int sum = 0;
        // ✅ 移动指针 做累加 长度不一致 也 继续加 、
        while (p1 != null || p2 != null || sum != 0) {
            if (p1 != null) {
                sum += p1.val;
                p1 = p1.next;
            }
            if (p2 != null) {
                sum += p2.val;
                p2 = p2.next;
            }
            // eg sum = 12
            p.next = new ListNode(sum % 10);
            p = p.next;
            sum /= 10;
        }
        return tmp.next;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode tmp = new ListNode(-1), p = tmp;
        while (list1 != null && list2 != null) { // 需要 对比大小、值要求有即非 null
            if (list1.val <= list2.val) {
                p.next = list1;
                list1 = list1.next; // ✅ 移动 list1
            } else {
                p.next = list2;
                list2 = list2.next; // ✅ 移动 list2
            }
            p = p.next;
        }
        // ✅ 剩余 l2 or l1 长度不一致 、做拼接
        p.next = list1 == null ? list2 : list1;
        return tmp.next;
    }


    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Map<Node, Node> map = new HashMap<>();
        Node cur = head;// 遍历指针
        // build map
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        // build new link
        Node tmp = new Node(-10001), p = tmp;
        cur = head;
        while (cur != null) {
            Node curN = map.get(cur);
            Node randomN = map.get(cur.random);
            p.next = curN;
            p = p.next;
            p.random = randomN;
            // 这里漏掉了：cur = cur.next;
            cur = cur.next;
        }
        return tmp.next;
    }

    //    public ListNode reverseBetween(ListNode head, int left, int right) {
//        if (head == null || left == right) return head;
//        // 输入：head = [1,2,3,4,5], left = 2, right = 4 --- 2,3,4
//        //  输出：[1,4,3,2,5] 涉及头节点
//        // dummy - [1,2,3,4,5],
//        ListNode dummy = new ListNode(-1, head);
//        ListNode pL = dummy , pR = dummy;
//        for (int i = 0; i < left-1; i++) pL = pL.next;
//        for (int i = 0; i < right; i++) pR = pR.next;
//        ListNode r = pR.next;
//        ListNode start = pL.next;
//        // ListNode newH = reverseA2B(start, pR); ❌ 注意右边节点是开区间
//        ListNode newH = reverseA2B(start, r);
//        pL.next = newH;
//        start.next = r;
//        return dummy.next;
//    }
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;
        ListNode dummy = new ListNode(-1, head);

        //1. 固定区间
        // head = [1,2,3,4,5], left = 2, right = 4
        // 到 1 （ 2 3 4 ） 5 、原 要 拼接反转
        ListNode preA = dummy, b = dummy;
        for (int i = 0; i < left - 1; i++) preA = preA.next;
        for (int i = 0; i < right; i++) b = b.next;
        ListNode a = preA.next;
        ListNode nextB = b.next;
        // ListNode newH = reverseA2B(a, b); ❌ 注意右边节点是开区间
        ListNode newH = reverseA2B(a, nextB);
        preA.next = newH;
        a.next = nextB;
        return dummy.next;
    }

    ListNode reverseKGroup(ListNode head, int k) {
        // 找 k 个先
        // head = [1,2,3,4,5], k = 2
        // 1,2,3 实际反转到 3 就 break 了
        ListNode a = head, b = head;
        for (int i = 0; i < k; i++) {
//            if (b！=null)  b = b.next;
//            break; ❌ 理解错了 应该是
            if (b == null) return head; // return break if  reverser
            b = b.next;
        }
        // 2 1 , newH = 2
        ListNode newHead = reverseA2B(a, b);
        a.next = reverseKGroup(b, k); // a就是尾巴了、然后递归逻辑即可
        return newHead;
    }

    ListNode reverseA2B(ListNode a, ListNode b) {
        ListNode pre = null, cur = a, nxt = a;
        while (cur != b) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        return pre;
    }


    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 输入：head = [1,2,3,4,5], n = 2
        // 输出：[1,2,3,5]
        ListNode tmp = new ListNode(-1, head);
        ListNode slow = tmp, fast = tmp;
        for (int i = 0; i < n; i++) fast = fast.next;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return tmp.next;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return head;// ✅避免 cur.next NPE

        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;// ✅逻辑就是 和 下一个一样就跳过 next
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    public ListNode deleteDuplicates2(ListNode head) {
        // 输入：head = tmp-[1,1,1,2,3]
        // 输出：[2,3] 、

        ListNode tmp = new ListNode(-1, head), cur = tmp;
        while (cur != null && cur.next != null && cur.next.next != null) {
            // cur.next.val == cur.next
            //while 条件讲解： 涉及.next .val防止
            if (cur.next.val == cur.next.next.val) { // ✅ dummy 开始 判断下 下下
                // 批量删除所有值为 x 的节点、直到不同 移动到 cuz.next = 2 、即第一个非 1
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) cur.next = cur.next.next;

                // 此时状态：cur.next 指向了第一个 2、这一轮结束了，cur 没有移动，依然指向 dummy
                // 下一轮会继续检查的 cur 的 下 下下
            } else {
                cur = cur.next;// 不相等才后移
            }
        }
        return tmp.next;
    }

    public ListNode deleteDuplicates2(ListNode head) {
        ListNode tmp = new ListNode(-1, head), cur = tmp;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {// cur.next.next.val
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return tmp.next;
    }

    public ListNode rotateRight(ListNode head, int k) {
        //   - 输入：head = [1,2,3,4,5], k = 2 、输出：[4,5,1,2,3]
            // 逻辑变成    1,2,3,4,5-
                        //|        |
                        //-----------
        // 拆开  3->4 发现 倒 2
        if (head == null || head.next == null || k == 0) return head;
        // 这种随机访问 用 list 做
        // 1. 计算链表长度并存储节点到列表
        List<ListNode> nodes = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            nodes.add(curr);
            curr = curr.next;
        }

        int n = nodes.size();
        k %= n;  // 取模，避免重复旋转
        if (k == 0) return head;  // 旋转n的倍数，相当于没旋转

        // 2. 连接成环
        nodes.get(n - 1).next = head;
        // 3. 在合适位置断开
        nodes.get(n - k - 1).next = null;
        // 4. 更新头节点
        head = nodes.get(n - k);
        return head;
    }

    public ListNode rotateRight2(ListNode head, int k) {
        // 边界条件：如果链表为空、只有一个节点，或者旋转 0 次，直接返回
        if (head == null || head.next == null || k == 0) return head;

        // ✅ 总计思路：先计算，记录oldTail，然后取模，然后从 head 到断开位置
        // 1. 计算链表长度 n，并找到原链表的最后一个节点 oldTail
        ListNode oldTail = head;
        int n = 1;
        while (oldTail.next != null) {
            oldTail = oldTail.next;
            n++;
        }

        // 2. 如果 k 是 n 的倍数，说明旋转后还是原样
        k %= n;
        if (k == 0) return head;

        // 3. 将链表连成环
        oldTail.next = head;

        // 4. 寻找新的尾节点（newTail）
        // 新的尾节点在距离头节点 n - k - 1 的位置
        // 比如 n=5, k=2, 则新尾在 5-2-1 = 2 的位置（从 head 走 2 步到达节点 3）
        ListNode newTail = head;
        for (int i = 0; i < n - k - 1; i++) newTail = newTail.next;

        // 5. 确定新的头节点，并断开环
        ListNode newHead = newTail.next;
        newTail.next = null;
        return newHead;
    }


    public ListNode partition(ListNode head, int x) {
        ListNode dummy1 = new ListNode(-1);  // 存放小于 x 的链表的虚拟头结点
        ListNode dummy2 = new ListNode(-1); // 存放大于等于 x 的链表的虚拟头结点
        ListNode p1 = dummy1, p2 = dummy2, p = head;
        while (p != null) {
            if (p.val >= x) {
                p2.next = p;
                p2 = p2.next;
            } else {
                p1.next = p;
                p1 = p1.next;
            }
            // ❌ 不能直接让 p 指针前进，p = p.next
            ListNode temp = p.next; // 断开原链表中的每个节点的 next 指针
            p.next = null;
            p = temp;
        }
        // 小 拼接 大
        p1.next = dummy2.next;
        return dummy1.next;
    }
}
