package com.smartcity.scc;

import com.smartcity.utils.Metrics;
import java.util.*;

public class SCCFinder {

    private static int time;
    private static Map<Integer, Integer> disc, low;
    private static Deque<Integer> stack;
    private static Set<Integer> onStack;
    private static List<List<Integer>> sccList;

    public static List<List<Integer>> findSCC(Map<Integer, List<int[]>> graph, Metrics metrics) {
        time = 0;
        disc = new HashMap<>();
        low = new HashMap<>();
        stack = new ArrayDeque<>();
        onStack = new HashSet<>();
        sccList = new ArrayList<>();

        metrics.start();
        for (Integer v : graph.keySet()) {
            if (!disc.containsKey(v)) {
                dfs(v, graph, metrics);
            }
        }
        metrics.stop();
        return sccList;
    }

    private static void dfs(int u, Map<Integer, List<int[]>> graph, Metrics metrics) {
        disc.put(u, time);
        low.put(u, time);
        time++;
        stack.push(u);
        onStack.add(u);
        metrics.dfsCalls++;

        for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
            int v = edge[0]; // предполагается формат [to, weight]
            if (!disc.containsKey(v)) {
                dfs(v, graph, metrics);
                low.put(u, Math.min(low.get(u), low.get(v)));
            } else if (onStack.contains(v)) {
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }

        // Найдена компонента сильной связности
        if (low.get(u).equals(disc.get(u))) {
            List<Integer> scc = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                onStack.remove(w);
                scc.add(w);
            } while (w != u);
            sccList.add(scc);
        }
    }
}
