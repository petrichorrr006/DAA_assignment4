package com.smartcity;

import com.smartcity.scc.*;
import com.smartcity.topo.*;
import com.smartcity.dagsp.*;
import com.smartcity.utils.*;
import com.smartcity.graph.*;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        String file;
        if (args.length > 0) {
            file = args[0];
        } else {
            file = "C:/Users/User/Desktop/DAA_assignment4/assignment4/data/large3.json";
        }

        System.out.println("Reading graph from: " + file);

        Map<Integer, List<int[]>> graph = GraphLoader.loadWeighted(file);

        Metrics metrics = new Metrics();
        metrics.start();
        List<List<Integer>> components = SCCFinder.findSCC(graph, metrics);
        metrics.stop();
        metrics.printSummary("SCC");
        System.out.println("Components: " + components);

        metrics = new Metrics();
        metrics.start();
        List<Integer> topoOrder = TopologicalSort.sort(graph, metrics);
        metrics.stop();
        metrics.printSummary("TopoSort");
        System.out.println("Topo order: " + topoOrder);

        boolean isDAG = topoOrder.size() == graph.size();
        if (!isDAG) {
            System.out.println("Graph has cycles — skipping DAG path algorithms.\n");
            return;
        }

        metrics = new Metrics();
        Map<Integer, Integer> shortest = DAGShortestPath.shortestPath(graph, topoOrder, 0, metrics);
        metrics.printSummary("DAG Shortest Path");

        System.out.println("Shortest distances from 0:");
        for (Map.Entry<Integer, Integer> entry : shortest.entrySet()) {
            int d = entry.getValue();
            System.out.printf("  %d -> %s%n",
                    entry.getKey(),
                    (d == Integer.MAX_VALUE ? "inf" : d));
        }

        metrics = new Metrics();
        Map<Integer, Integer> longest = DAGShortestPath.longestPath(graph, topoOrder, 0, metrics);
        metrics.printSummary("DAG Longest Path");

        System.out.println("Longest distances from 0:");
        for (Map.Entry<Integer, Integer> entry : longest.entrySet()) {
            int d = entry.getValue();
            System.out.printf("  %d -> %s%n",
                    entry.getKey(),
                    (d == Integer.MIN_VALUE ? "-inf" : d));
        }
    }
}