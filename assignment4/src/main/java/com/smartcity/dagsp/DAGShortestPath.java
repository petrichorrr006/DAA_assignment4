package com.smartcity.dagsp;

import com.smartcity.utils.Metrics;
import java.util.*;

public class DAGShortestPath {

    public static Map<Integer, Integer> shortestPath(Map<Integer, List<int[]>> graph, int source, List<Integer> topoOrder, Metrics metrics) {

        metrics.start();
        Map<Integer, Integer> dist = new HashMap<>();
        for (int v : graph.keySet())
            dist.put(v, Integer.MAX_VALUE);
        dist.put(source, 0);

        for (int v : topoOrder) {
            if (dist.get(v) != Integer.MAX_VALUE) {
                for (int[] edge : graph.getOrDefault(v, List.of())) {
                    int to = edge[0], w = edge[1];
                    metrics.relaxations++;
                    if (dist.get(v) + w < dist.get(to)) {
                        dist.put(to, dist.get(v) + w);
                    }
                }
            }
        }
        metrics.stop();
        return dist;
    }

    public static Map<Integer, Integer> longestPath(Map<Integer, List<int[]>> graph, int source, List<Integer> topoOrder, Metrics metrics) {
        
        metrics.start();
        Map<Integer, Integer> dist = new HashMap<>();
        for (int v : graph.keySet())
            dist.put(v, Integer.MIN_VALUE);
        dist.put(source, 0);

        for (int v : topoOrder) {
            if (dist.get(v) != Integer.MIN_VALUE) {
                for (int[] edge : graph.getOrDefault(v, List.of())) {
                    int to = edge[0], w = edge[1];
                    metrics.relaxations++;
                    if (dist.get(v) + w > dist.get(to)) {
                        dist.put(to, dist.get(v) + w);
                    }
                }
            }
        }
        metrics.stop();
        return dist;
    }
}
