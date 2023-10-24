package com.example.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;


@WebServlet("/generateRandomTree")
public class RandomTreeServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RandomTreeServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String numVerticesStr = request.getParameter("numVertices");
        int numVertices;
        try {
            numVertices = Integer.parseInt(numVerticesStr);
        } catch (NumberFormatException e) {
            out.println("<p>Invalid input: Please enter a valid integer for the number of vertices.</p>");
            return;
        }

        if (numVertices < 1) {
            out.println("<p>Please enter a positive integer for the number of vertices.</p>");
            return;
        }

        int[][] adjacencyMatrix = generateRandomTree(numVertices);
        printAdjacencyMatrix(adjacencyMatrix, out);

        logRequestInfo(request);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Random Tree Generator</title></head><body>");
        out.println("<h1>Random Tree Generator</h1>");
        out.println("<form action='' method='post'>");
        out.println("Number of Vertices: <input type='number' name='numVertices'/>");
        out.println("<input type='submit' value='Generate'/>");
        out.println("</form></body></html>");

        logRequestInfo(request);
    }

    private int[][] generateRandomTree(int numVertices) {
        int[][] adjacencyMatrix = new int[numVertices][numVertices];
        boolean[] visited = new boolean[numVertices];
        Random random = new Random();

        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            vertices.add(i);
        }

        int firstVertex = vertices.remove(random.nextInt(vertices.size()));
        visited[firstVertex] = true;

        while (!vertices.isEmpty()) {
            int secondVertex = vertices.remove(random.nextInt(vertices.size()));
            int connectedVertex = getConnectedVertex(visited, numVertices, random);
            adjacencyMatrix[secondVertex][connectedVertex] = 1;
            adjacencyMatrix[connectedVertex][secondVertex] = 1;
            visited[secondVertex] = true;
        }
        return adjacencyMatrix;
    }

    private int getConnectedVertex(boolean[] visited, int numVertices, Random random) {
        int vertex;
        do {
            vertex = random.nextInt(numVertices);
        } while (!visited[vertex]);
        return vertex;
    }

    private void printAdjacencyMatrix(int[][] matrix, PrintWriter out) {
        out.println("<h2>Adjacency Matrix:</h2>");
        out.println("<table border='1'>");
        for (int[] row : matrix) {
            out.println("<tr>");
            for (int cell : row) {
                out.println("<td>" + cell + "</td>");
            }
            out.println("</tr>");
        }
        out.println("</table>");
    }

    private void logRequestInfo(HttpServletRequest request) {
        logger.info("HTTP Method: " + request.getMethod());
        logger.info("Client IP Address: " + request.getRemoteAddr());
        logger.info("User-Agent: " + request.getHeader("User-Agent"));

        Enumeration<Locale> locales = request.getLocales();
        StringBuilder languages = new StringBuilder();
        while (locales.hasMoreElements()) {
            Locale locale = locales.nextElement();
            languages.append(locale.toString()).append(", ");
        }
        logger.info("Client Languages: " + languages);

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            logger.info("Request Parameter - " + paramName + ": " + paramValue);
        }
    }
}
