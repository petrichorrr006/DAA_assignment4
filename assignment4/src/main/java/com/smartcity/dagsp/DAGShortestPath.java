package com.smartcity.dagsp;

import java.util.*;
import com.smartcity.utils.Metrics;

public class DAGShortestPath {

    public static Map<Integer, Integer> shortestPath(
            Map<Integer, List<int[]>> graph,
            List<Integer> topoOrder,
            int start,
            Metrics metrics) {

        Map<Integer, Integer> dist = new HashMap<>();

        for (Integer v : graph.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
        }
        for (List<int[]> edges : graph.values()) {
            for (int[] e : edges) {
                if (!dist.containsKey(e[0])) dist.put(e[0], Integer.MAX_VALUE);
                if (!dist.containsKey(e[1])) dist.put(e[1], Integer.MAX_VALUE);
            }
        }

        if (!dist.containsKey(start)) {
            System.out.println("⚠️ Start vertex " + start + " not found in graph!");
            return dist;
        }

        dist.put(start, 0);
        metrics.start();

        for (int u : topoOrder) {
            if (dist.get(u) != Integer.MAX_VALUE) {
                for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                    int v = edge[0];
                    int w = edge[1];
                    if (dist.get(v) > dist.get(u) + w) {
                        dist.put(v, dist.get(u) + w);
                        metrics.relaxations++;
                    }
                }
            }
        }

        metrics.stop();
        return dist;
    }

    public static Map<Integer, Integer> longestPath(
            Map<Integer, List<int[]>> graph,
            List<Integer> topoOrder,
            int start,
            Metrics metrics) {

        Map<Integer, Integer> dist = new HashMap<>();

        for (Integer v : graph.keySet()) {
            dist.put(v, Integer.MIN_VALUE);
        }
        for (List<int[]> edges : graph.values()) {
            for (int[] e : edges) {
                if (!dist.containsKey(e[0])) dist.put(e[0], Integer.MIN_VALUE);
                if (!dist.containsKey(e[1])) dist.put(e[1], Integer.MIN_VALUE);
            }
        }

        if (!dist.containsKey(start)) {
            System.out.println("⚠️ Start vertex " + start + " not found in graph!");
            return dist;
        }

        dist.put(start, 0);
        metrics.start();

        for (int u : topoOrder) {
            if (dist.get(u) != Integer.MIN_VALUE) {
                for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                    int v = edge[0];
                    int w = edge[1];
                    if (dist.get(v) < dist.get(u) + w) {
                        dist.put(v, dist.get(u) + w);
                        metrics.relaxations++;
                    }
                }
            }
        }

        metrics.stop();
        return dist;
    }
}
