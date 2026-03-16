package 手撕;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    // ✅ 记住这个结构
    private static class Node {
        int key, value;
        Node prev, next;
        // ✅ Cmd + N (macOS)
        public Node(int value, int key) {
            this.value = value;
            this.key = key;
        }
    }

    private final int capacity;
    private final Node dummy = new Node(0, 0);
    private final Map<Integer, Node> keyToNode = new HashMap<>();
    public LRUCache(int capacity) {
        this.capacity = capacity;
        // ✅ 初始化才做 链表逻辑
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

    private Node getNode(int key) {
        if (!keyToNode.containsKey(key)) return null;
        // ✅ 有个话 需要移动位置到前面
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
