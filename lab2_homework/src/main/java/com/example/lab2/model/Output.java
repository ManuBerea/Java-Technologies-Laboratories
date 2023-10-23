package com.example.lab2.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Output {
    private final Input inputData;
    private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
    private Integer verticesCount, edgesCount, connectedComponentsCount;
    private boolean[] visitStatus;

    public Output(Input inputData) {
        this.inputData = inputData;
    }

    public void processGraph() {
        try (InputStream inputStream = inputData.getGraphFile().getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            bufferedReader.lines()
                    .map(String::trim)
                    .filter(line -> line.startsWith("p edge") || line.startsWith("e "))
                    .forEach(this::processLine);

            if (verticesCount == null || edgesCount == null) {
                throw new IllegalStateException("Graph file is missing the 'p edge' line or it is not formatted correctly.");
            }

            this.connectedComponentsCount = calculateConnectedComponents();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void processLine(String line) {
        if (line.startsWith("p edge")) {
            String[] parts = line.split(" ");
            if (parts.length >= 4) {
                this.verticesCount = Integer.parseInt(parts[2]);
                this.edgesCount = Integer.parseInt(parts[3]);
            }
        } else if (line.startsWith("e ")) {
            String[] parts = line.split(" ");
            int vertex1 = Integer.parseInt(parts[1]);
            int vertex2 = Integer.parseInt(parts[2]);
            addEdge(vertex1, vertex2);
        }
    }

    private void addEdge(int vertex1, int vertex2) {
        adjacencyList.computeIfAbsent(vertex1, k -> new ArrayList<>()).add(vertex2);
        adjacencyList.computeIfAbsent(vertex2, k -> new ArrayList<>()).add(vertex1);
    }

    private Integer calculateConnectedComponents() {
        visitStatus = new boolean[verticesCount + 1];
        Integer componentsCounter = 0;

        for (int vertex : adjacencyList.keySet()) {
            if (!visitStatus[vertex]) {
                depthFirstSearch(vertex);
                componentsCounter++;
            }
        }

        return componentsCounter;
    }

    private void depthFirstSearch(int vertex) {
        visitStatus[vertex] = true;
        adjacencyList.getOrDefault(vertex, Collections.emptyList())
                .stream()
                .filter(neighbor -> !visitStatus[neighbor])
                .forEach(this::depthFirstSearch);
    }

    public Integer getOrder() {
        return verticesCount;
    }

    public Integer getSize() {
        return edgesCount;
    }

    public Integer getNumberOfConnectedComponents() {
        return connectedComponentsCount;
    }
}
