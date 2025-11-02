package com.smartcity.utils;

public class Metrics {
    public long dfsCalls = 0;
    public long relaxations = 0;
    public long queueOps = 0;
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long elapsed() {
        return endTime - startTime;
    }

    public void printSummary(String name) {
        System.out.printf("[%s] time = %d ns, dfs = %d, relax = %d, queueOps = %d%n",
                name, elapsed(), dfsCalls, relaxations, queueOps);
    }
}