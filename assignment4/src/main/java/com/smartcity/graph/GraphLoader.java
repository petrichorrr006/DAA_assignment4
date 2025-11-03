package com.smartcity.graph;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class GraphLoader {

    public static Map<Integer, List<int[]>> loadWeighted(String path) {
        Map<Integer, List<int[]>> graph = new HashMap<>();

        try (Reader reader = new FileReader(path)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

            JsonArray nodes = json.getAsJsonArray("nodes");
            for (JsonElement n : nodes) {
                int node = n.getAsInt();
                graph.putIfAbsent(node, new ArrayList<>());
            }

            JsonArray edges = json.getAsJsonArray("edges");
            for (JsonElement e : edges) {
                JsonObject o = e.getAsJsonObject();
                int from = o.get("from").getAsInt();
                int to = o.get("to").getAsInt();
                int w = o.has("weight") ? o.get("weight").getAsInt() : 1;

                graph.putIfAbsent(from, new ArrayList<>());
                graph.putIfAbsent(to, new ArrayList<>());
                graph.get(from).add(new int[]{to, w});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }

    public static Map<Integer, List<Integer>> toUnweighted(Map<Integer, List<int[]>> weighted) {
        Map<Integer, List<Integer>> g = new HashMap<>();
        for (var e : weighted.entrySet()) {
            List<Integer> list = new ArrayList<>();
            for (int[] edge : e.getValue()) list.add(edge[0]);
            g.put(e.getKey(), list);
        }
        return g;
    }
}
