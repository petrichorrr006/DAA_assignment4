Assignment 4 Report — Graph Algorithms Performance Analysis
1. Introduction

This report presents the performance analysis of four graph algorithms implemented in Java:

Tarjan’s Strongly Connected Components (SCC)
Topological Sort (Kahn’s Algorithm)
DAG Shortest Path
DAG Longest Path

The experiments were conducted on three categories of directed graphs (DAGs and cyclic graphs):
small, medium, and large, each generated with different node and edge counts.

2. Dataset Description

The input graphs were divided into three categories:

Small: 6–10 nodes,	5–23 edges, simple test graphs for correctness
Medium:	10–20	nodes, 8–77	edges, intermediate-sized graphs
Large:	20–50 nodes,	10–152	edges, performance and scalability tests

Only the 1.json graphs in each category are acyclic (DAG) — 2.json and 3.json contain cycles to test the SCC algorithm.

3. Metrics Collected

Each algorithm records the following metrics:

time (ns) -	Execution time in nanoseconds
dfsCalls - Number of depth-first search calls
relaxations	- Number of edge relaxations (for path algorithms)
queueOps - Queue operations in Kahn’s algorithm

These were automatically measured using the Metrics utility class.

4. Results Summary
<img width="1179" height="201" alt="image" src="https://github.com/user-attachments/assets/10c88b52-0347-4a28-9e5d-7a4eef97c9da" />

5. Analysis

5.1 SCC Performance
The SCC algorithm scales linearly with the number of edges and nodes.
The main bottleneck is the recursive DFS stack growth for large, dense graphs.
As graph density increases from ~84,000 ns (small) to ~255,000 ns (large), the number of back edges and low-link updates rises, increasing memory overhead slightly but still maintaining linear complexity O(V + E).

5.2 Topological Sort

TopoSort performed efficiently across all graph sizes, increasing from 30,000 ns → 97,000 ns.
Time complexity is O(V + E), so growth is near-linear. Performance depends on in-degree distribution. Sparse DAGs perform best since fewer queue operations occur.
In dense graphs, many nodes are added and removed from the queue, slightly increasing time.

5.3 DAG Shortest and Longest Path

Executed only on acyclic graphs (1.json files).
Both algorithms showed similar runtimes (20,000–40,000 ns).The key bottleneck is the relaxation phase. For dense DAGs, the number of relaxations approaches the number of edges (E).
Path computation is efficient for sparse graph but slower in denser ones due to increased edge traversal.

Choice of Weights:
In this implementation, the DAG algorithms use edge weights rather than node durations.
Each edge represents a directed connection with a specific weight (e.g., cost or time).
This choice allows more flexible modeling of real-world dependencies and makes the shortest/longest path computation directly reflect connection strength between nodes.
The weight data are stored and parsed from the "weight" field in the JSON files.

6. Discussion

As graph size increases, all algorithms show predictable linear or near-linear scaling. SCC is more computationally intensive due to recursive DFS and stack operations. Topological Sort and DAG paths are faster but limited to DAG inputs.
The algorithms handle both cyclic and acyclic cases correctly:
Cycles → detected via SCC
DAGs → processed with TopoSort + Path algorithms

7. Conclusion

The experimental results confirm that the implementations are functionally correct and efficient. Performance grows roughly linearly with the number of vertices and edges.
The system properly differentiates between cyclic and acyclic graphs and applies the correct algorithms in each case. This validates the correctness and scalability of all four algorithms.
When to Use Each Algorithm:
SCCFinder (Tarjan) — Use when detecting strongly connected structures or validating DAG conditions. Essential for identifying cyclic dependencies in project scheduling or network analysis.
Topological Sort — Use only for acyclic graphs where dependency ordering is required (e.g., task scheduling, compiler dependency resolution).
DAG Shortest/Longest Path — Apply when computing weighted dependency chains, such as critical path analysis or minimal dependency resolution in workflows.
