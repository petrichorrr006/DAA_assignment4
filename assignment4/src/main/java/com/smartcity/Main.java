package com.smartcity;

import com.smartcity.scc.*;
import com.smartcity.topo.*;
import com.smartcity.dagsp.*;
import com.smartcity.utils.*;
import com.smartcity.graph.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String file = args.length > 0 ? args[0] : "data/small1.json";
        System.out.println("Reading graph from: " + file);

        var weighted = GraphLoader.loadWeighted(file);
        var unweighted = GraphLoader.toUnweighted(weighted);

        Metrics sccMetrics = new Metrics();
        SCCFinder scc = new SCCFinder(unweighted, sccMetrics);
        var components = scc.findSCCs();
        sccMetrics.printSummary("SCC");
        System.out.println("Components: " + components);

        Metrics topoMetrics = new Metrics();
        var topo = TopologicalSort.kahn(unweighted, topoMetrics);
        topoMetrics.printSummary("TopoSort");
        System.out.println("Topo order: " + topo);

        if (!topo.isEmpty()) {
            Metrics spMetrics = new Metrics();
            var dist = DAGShortestPath.shortestPath(weighted, 0, topo, spMetrics);
            spMetrics.printSummary("DAG Shortest Path");
            System.out.println("Shortest paths from 0: " + dist);

            Metrics lpMetrics = new Metrics();
            var longest = DAGShortestPath.longestPath(weighted, 0, topo, lpMetrics);
            lpMetrics.printSummary("DAG Longest Path");
            System.out.println("Longest paths from 0: " + longest);
        } else {
            System.out.println("Graph has cycles — skip DAG path algorithms.");
        }
    }
}