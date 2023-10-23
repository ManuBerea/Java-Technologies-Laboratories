package com.example.lab2.logic;

import com.example.lab2.model.Input;
import com.example.lab2.model.Output;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "graphProcessingServlet", urlPatterns = "/processing")
@MultipartConfig
public class ProcessingServlet extends HttpServlet {
    private final Input graphInput = new Input();
    private final Output graphOutput = new Output(graphInput);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        Part graphFile = request.getPart("graphFile");
        graphInput.setGraphFile(graphFile);
        graphOutput.processGraph();

        request.setAttribute("graph", graphOutput);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
