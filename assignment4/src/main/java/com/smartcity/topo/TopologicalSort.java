package com.smartcity.topo;

import com.smartcity.utils.Metrics;
import java.util.*;

public class TopologicalSort {

    public static List<Integer> sort(Map<Integer, List<int[]>> graph, Metrics metrics) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> indegree = new HashMap<>();

        // Подсчёт входящих рёбер
        for (Integer u : graph.keySet()) {
            indegree.putIfAbsent(u, 0);
            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                indegree.put(v, indegree.getOrDefault(v, 0) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> e : indegree.entrySet()) {
            if (e.getValue() == 0) queue.add(e.getKey());
        }

        metrics.start();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            metrics.queueOps++;
            result.add(u);

            for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                int v = edge[0];
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) queue.add(v);
            }
        }
        metrics.stop();

        return result;
    }
}
