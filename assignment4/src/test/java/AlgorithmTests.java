import com.smartcity.utils.Metrics;
import com.smartcity.scc.SCCFinder;
import com.smartcity.topo.TopologicalSort;
import com.smartcity.dagsp.DAGShortestPath;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class AlgorithmTests {

    @Test
    public void testSimpleDAGShortestPath() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, List.of(new int[]{1, 3}, new int[]{2, 2}));
        graph.put(1, List.of(new int[]{3, 4}));
        graph.put(2, List.of(new int[]{3, 1}));
        graph.put(3, new ArrayList<>());

        List<Integer> topo = List.of(0, 1, 2, 3);
        Metrics m = new Metrics();

        Map<Integer, Integer> dist = DAGShortestPath.shortestPath(graph, topo, 0, m);
        assertEquals(0, dist.get(0));
        assertEquals(3, dist.get(1));
        assertEquals(2, dist.get(2));
        assertEquals(3, dist.get(3)); 
    }

    @Test
    public void testTopologicalSort() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, List.of(new int[]{1, 1}, new int[]{2, 1}));
        graph.put(1, List.of(new int[]{3, 1}));
        graph.put(2, List.of(new int[]{3, 1}));
        graph.put(3, new ArrayList<>());

        Metrics m = new Metrics();
        List<Integer> topo = TopologicalSort.sort(graph, m);

        assertTrue(topo.indexOf(0) < topo.indexOf(1));
        assertTrue(topo.indexOf(0) < topo.indexOf(2));
        assertTrue(topo.indexOf(1) < topo.indexOf(3));
        assertTrue(topo.indexOf(2) < topo.indexOf(3));
    }

    @Test
    public void testSCCFinder() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, List.of(new int[]{1, 1}));
        graph.put(1, List.of(new int[]{2, 1}));
        graph.put(2, List.of(new int[]{0, 1}));
        graph.put(3, new ArrayList<>());

        Metrics m = new Metrics();
        List<List<Integer>> scc = SCCFinder.findSCC(graph, m);

        assertEquals(2, scc.size());
        assertTrue(scc.stream().anyMatch(c -> c.containsAll(List.of(0,1,2))));
    }

    @Test
    public void testEmptyGraph() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        Metrics m = new Metrics();

        List<List<Integer>> scc = SCCFinder.findSCC(graph, m);
        List<Integer> topo = TopologicalSort.sort(graph, m);

        assertTrue(scc.isEmpty());
        assertTrue(topo.isEmpty());
    }

    @Test
    public void testSingleNodeGraph() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, new ArrayList<>());

        Metrics m = new Metrics();
        List<List<Integer>> scc = SCCFinder.findSCC(graph, m);
        List<Integer> topo = TopologicalSort.sort(graph, m);

        assertEquals(1, scc.size());
        assertEquals(List.of(0), topo);
    }

    @Test
    public void testCycleGraphNotDAG() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, List.of(new int[]{1, 1}));
        graph.put(1, List.of(new int[]{0, 1}));

        Metrics m = new Metrics();
        List<Integer> topo = TopologicalSort.sort(graph, m);
        assertTrue(topo.size() < graph.size());
    }

    @Test
    public void testUnreachableNode() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, List.of(new int[]{1, 1}));
        graph.put(1, new ArrayList<>());
        graph.put(2, new ArrayList<>());

        List<Integer> topo = List.of(0, 1, 2);
        Metrics m = new Metrics();

        Map<Integer, Integer> dist = DAGShortestPath.shortestPath(graph, topo, 0, m);
        assertEquals(Integer.MAX_VALUE, dist.get(2));
    }


}