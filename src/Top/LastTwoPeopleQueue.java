package Top;

import java.util.LinkedList;
import java.util.Queue;

public class LastTwoPeopleQueue {
    /**
     * 队列版模拟法：高效求最后剩下的两个人的位置
     * @param total 总人数（≥2）
     * @return 最后剩下的两个位置（从小到大排序）
     */
    public static int[] findLastTwo(int total) {
        // 完善的边界校验：提示更清晰，符合生产代码规范
        if (total < 2) {
            throw new IllegalArgumentException("参数异常：总人数必须≥2，当前输入为" + total);
        }
        if (total == 2) { // 提前返回，减少不必要的循环
            return new int[]{1, 2};
        }

        // 初始化队列：存储1~total的位置编号
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= total; i++) {
            queue.offer(i);
        }

        // 循环剔除，直到只剩2人
        while (queue.size() > 2) {
            int currentSize = queue.size(); // 记录本轮初始人数（避免遍历中队列长度变化）
            boolean keep = true; // 标记：是否保留当前元素（隔一个保留）

            // 遍历本轮所有元素：隔一个留一个
            for (int i = 0; i < currentSize; i++) {
                int person = queue.poll(); // 取出队首元素
                if (keep) {
                    queue.offer(person); // 保留：重新入队
                }
                keep = !keep; // 切换标记（保留→剔除→保留...）
            }
        }

        // 提取结果（队列中剩余2个元素）
        int first = queue.poll();
        int second = queue.poll();
        // 保证结果从小到大（极端情况队列顺序可能反转，比如n=5时队列最后是[5,1]）
        return new int[]{Math.min(first, second), Math.max(first, second)};
    }

    // 测试用例（和之前结果一致）
    public static void main(String[] args) {
        int[] testCases = {2, 5, 8, 10, 15, 10000}; // 新增大数测试
        for (int n : testCases) {
            long start = System.currentTimeMillis();
            int[] result = findLastTwo(n);
            long cost = System.currentTimeMillis() - start;
            System.out.printf("总人数%d → 最后剩下的位置：%d、%d | 耗时：%dms%n",
                    n, result[0], result[1], cost);
        }
    }
}