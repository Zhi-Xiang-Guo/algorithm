package 手撕.LRU;

import java.util.HashMap;

/**
 * @author yourname guozhixiang03
 * Created on 2026-02-28
 */
public class LRU {
    public static void main(String[] args) {
        LRU lru = new LRU(2);
        lru.put(1, 1); // cache: [1]
        lru.put(2, 2); // cache: [2, 1]
        System.out.println(lru.get(1)); // 返回 1, cache: [1, 2]
        // 测试LRU 删除场景
        lru.put(3, 3); // 容量满，删 2, cache: [3, 1]
        System.out.println(lru.get(2)); // 返回 -1 (找不到)
    }

    private int capacity;
    private HashMap<Integer, Node> keyToNode;
    // ✅ 哨兵节点：dummy.next 是最新访问的(Head)，dummy.pre 是最久未访问的(Tail)
    private Node dummy;

    public LRU(int capacity) {
        this.capacity = capacity;
        this.keyToNode = new HashMap<>();

        // 初始化双向循环链表
        this.dummy = new Node(-1, -1);
        dummy.pre = dummy;
        dummy.next = dummy;
    }


    // get 逻辑 - 返回值
    public int get(int key) {
        Node node = keyToNode.get(key);
        if (node == null) return -1;
        // 存在：先从当前位置删掉，再移到头部
        remove(node);
        addToHead(node);
        return node.val;
    }

    // add 逻辑：存在即更新并更新LRU 、
    public void put(int key, int value) {
        Node node = keyToNode.get(key);
        if (node != null) {
            // 1. key 已存在：更新 value，并移到头部
            node.val = value;
            remove(node);
            addToHead(node);
        } else {
            // 2. key 不存在：创建新节点
            Node newNode = new Node(key, value);

            // 如果容量满了，淘汰最久未使用的（即 dummy.pre）
            if (keyToNode.size() >= capacity) {
                Node tail = dummy.pre; // 这一步体现了双向链表的优势 O(1)
                remove(tail);          // 链表中移除
                keyToNode.remove(tail.key); // Map中移除
            }

            // 添加新节点到头部 和 Map
            addToHead(newNode);
            keyToNode.put(key, newNode);
        }
    }

    // 再showoff自己抽取代码能力
    private Node getNode(int key) {
        if (!keyToNode.containsKey(key)) return null;
        Node node = keyToNode.get(key);
        remove(node);
        addToHead(node);
        return node;
    }


    // 从链表中移除节点 x (断开链接)
    private void remove(Node x) {
        x.pre.next = x.next;
        x.next.pre = x.pre;
    }

    // 将节点 x 插入到 dummy 后面（即成为新的头节点）✅ 先x连接、在让其他连接x
    // dummy 1 2 eg 插入3
    // dummy 3 1 2
    private void addToHead(Node x) {
       x.pre = dummy;
       x.next = dummy.next;
       dummy.next = x;
       x.next.pre = x;
    }

    class Node {
        int key, val;
        Node pre, next;
        public Node(int val, int key) {
            this.val = val;
            this.key = key;
        }
    }

}
