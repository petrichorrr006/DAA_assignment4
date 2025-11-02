package com.smartcity.topo;

import com.smartcity.utils.Metrics;
import java.util.*;

public class TopologicalSort {

    public static List<Integer> kahn(Map<Integer, List<Integer>> graph, Metrics metrics) {
        metrics.start();

        Map<Integer, Integer> indegree = new HashMap<>();
        for (int v : graph.keySet()) {
            indegree.putIfAbsent(v, 0);
            for (int to : graph.get(v)) {
                indegree.put(to, indegree.getOrDefault(to, 0) + 1);
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (var e : indegree.entrySet())
            if (e.getValue() == 0)
                queue.add(e.getKey());

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int v = queue.poll();
            metrics.queueOps++;
            order.add(v);
            for (int to : graph.getOrDefault(v, List.of())) {
                indegree.put(to, indegree.get(to) - 1);
                if (indegree.get(to) == 0)
                    queue.add(to);
            }
        }

        metrics.stop();
        return order;
    }
}
