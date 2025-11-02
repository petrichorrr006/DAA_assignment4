package com.smartcity.scc;

import com.smartcity.utils.Metrics;
import java.util.*;

public class SCCFinder {
    private final Map<Integer, List<Integer>> graph;
    private final List<List<Integer>> components = new ArrayList<>();
    private final Metrics metrics;

    private final Map<Integer, Integer> disc = new HashMap<>();
    private final Map<Integer, Integer> low = new HashMap<>();
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Set<Integer> inStack = new HashSet<>();
    private int time = 0;

    public SCCFinder(Map<Integer, List<Integer>> graph, Metrics metrics) {
        this.graph = graph;
        this.metrics = metrics;
    }

    public List<List<Integer>> findSCCs() {
        metrics.start();
        for (int v : graph.keySet()) {
            if (!disc.containsKey(v))
                dfs(v);
        }
        metrics.stop();
        return components;
    }

    private void dfs(int v) {
        metrics.dfsCalls++;
        disc.put(v, time);
        low.put(v, time);
        time++;
        stack.push(v);
        inStack.add(v);

        for (int to : graph.getOrDefault(v, List.of())) {
            if (!disc.containsKey(to)) {
                dfs(to);
                low.put(v, Math.min(low.get(v), low.get(to)));
            } else if (inStack.contains(to)) {
                low.put(v, Math.min(low.get(v), disc.get(to)));
            }
        }

        if (Objects.equals(low.get(v), disc.get(v))) {
            List<Integer> comp = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                inStack.remove(w);
                comp.add(w);
            } while (w != v);
            components.add(comp);
        }
    }
}