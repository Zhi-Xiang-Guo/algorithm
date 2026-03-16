package 手撕;

import java.util.HashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class LRUWithTTL {
    public static void main(String[] args) throws InterruptedException {
        // 测试过期逻辑
        // 加入 1s 两个
        LRUWithTTL lru = new LRUWithTTL(2, 1000);
        lru.put(1,1,1000);
        lru.put(2,2,1000);
        System.out.println(lru.get(1));
        Thread.sleep(999);
        System.out.println(lru.get(1));
    }

    private final int capacity;
    private final long defaultTTL;
    private final Node dummy;
    private final HashMap<Integer, Node> keyToNode;
    // DelayQueue：只负责“按时间取出到期元素”
    private final DelayQueue<Node> delayQueue = new DelayQueue<>();
    public LRUWithTTL(int capacity, long defaultTTL) {
        this.capacity = capacity;
        this.defaultTTL = defaultTTL;
        this.keyToNode = new HashMap<>();

        // 初始化双向链表
        this.dummy = new Node(-1, -1, Long.MAX_VALUE);
        dummy.pre = dummy;
        dummy.next = dummy;

        // 初始化时启动清理线程
        Thread cleanupThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Node expiredNode = delayQueue.take();
                    remove(expiredNode);
                    keyToNode.remove(expiredNode.key);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "lru-ttl-cleanup");
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }

    public int get(int key) {
        Node node = getNode(key);
        return node == null ? -1 : node.val;
    }

    public void put(int key, int value) {
        put(key, value, defaultTTL);
    }

    public void put(int key, int value, long ttlMillis) {
        // 校验时间 if (ttlMillis <= 0) {

        // ✅ 存储到期时间
        long expireTime = System.currentTimeMillis() + ttlMillis;
        // 复用 getNode：命中会移动到头部；过期会删除并返回 null
        Node node = getNode(key);
        if (node != null) {
            node.val = value;
            node.expireTime = expireTime;
            // ✅ DelayQueue 不会感知元素 delay 变化：remove + offer
            delayQueue.remove(node);
            delayQueue.offer(node);
            return;
        }
        // 返回 null 、准备新增
        Node newNode = new Node(key, value, expireTime);
        addToHead(newNode);
        keyToNode.put(key, newNode);
        delayQueue.offer(newNode);

        if (keyToNode.size() > capacity) {
            Node tail = dummy.pre;
            remove(tail);
            keyToNode.remove(tail.key);
            delayQueue.remove(tail);
        }
    }

    /**
     * 复用 LRUCache 的 getNode 思路：
     * - 不存在：返回 null
     * - 过期：在这里删除并返回 null
     * - 存在未过期：移动到头部并返回 node
     */
    private Node getNode(int key) {
        if (!keyToNode.containsKey(key)) return null;
        Node node = keyToNode.get(key);
        // ✅ 主动 check 过期 + 移除
        if (isExpired(node)) {
            remove(node);
            keyToNode.remove(key);
            delayQueue.remove(node);
            return null;
        }
        remove(node);
        addToHead(node);
        return node;
    }

    private boolean isExpired(Node node) {
        return System.currentTimeMillis() > node.expireTime;
    }

    private void remove(Node x) {
        x.pre.next = x.next;
        x.next.pre = x.pre;
    }

    private void addToHead(Node x) {
        x.pre = dummy;
        x.next = dummy.next;
        dummy.next.pre = x;
        dummy.next = x;
    }

    /**
     * Node 同时放在：
     * - HashMap（O(1) 找到）
     * - 双向链表（O(1) 调整 LRU）
     * - DelayQueue（到期触发清理）
     */
    private static class Node implements Delayed {
        int key, val;
        long expireTime;
        Node pre, next;

        Node(int key, int val, long expireTime) {
            this.key = key;
            this.val = val;
            this.expireTime = expireTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.expireTime, ((Node) o).expireTime);
        }
    }
}
