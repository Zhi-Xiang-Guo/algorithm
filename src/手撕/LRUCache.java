package 手撕;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    // ✅ 记住这个结构
    // ✅ Cmd + N (macOS)
    private static class Node {
        int key, value;
        Node prev, next;

        public Node(int value, int key) {
            this.value = value;
            this.key = key;
        }
    }

    private final int capacity;
    private final Node dummy;
    private final Map<Integer, Node> keyToNode; //  val -Node

    public LRUCache(int capacity) {
        this.capacity = capacity;
        keyToNode = new HashMap<>();
        // ✅ 初始化才做 链表逻辑
        dummy = new Node(0, 0); // 面试吹说 懒加载 不写上面 导致
        dummy.prev = dummy;
        dummy.next = dummy;
    }

    public int get(int key) {
        Node node = getNode(key);
        return node != null ? node.value : -1;
    }

    public void put(int key, int value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }
        node = new Node(key, value);
        keyToNode.put(key, node);
        pushFront(node);
        // ✅check 大小
        if (keyToNode.size() > capacity) {
            Node backNode = dummy.prev;
            keyToNode.remove(backNode.key);
            remove(backNode);
        }
    }

    // ✅ 对 ndoe 访问 有 lru 放前面 的逻辑、抽取放啊
    private Node getNode(int key) {
        if (!keyToNode.containsKey(key)) return null;
        Node node = keyToNode.get(key);
        remove(node);
        pushFront(node);
        return node;
    }

    private void remove(Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }

    private void pushFront(Node x) {
        x.prev = dummy;
        x.next = dummy.next;
        // 先对 x 处理

        x.prev.next = x;
        x.next.prev = x;
    }
}
