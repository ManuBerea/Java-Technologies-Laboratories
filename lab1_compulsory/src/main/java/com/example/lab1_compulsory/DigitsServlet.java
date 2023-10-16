package com.example.lab1_compulsory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

@WebServlet(name = "numberServlet", value = "/digits-servlet")
public class DigitsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String numberInput = req.getParameter("number");
        PrintWriter writer = resp.getWriter();

        try {
            int number = Integer.parseInt(numberInput);
            LinkedList<Integer> digits = extractDigits(number);

            writer.println("<p>Number: " + number + "</p>");
            if (!digits.isEmpty()) {
                writer.println("<ol>");
                for (Integer digit : digits) {
                    writer.println("<li>" + digit + "</li>");
                }
                writer.println("</ol>");
            } else {
                printErrorMessage(writer, "Positive numbers only.");
            }
        } catch (NumberFormatException ex) {
            printErrorMessage(writer, "Input is not a valid number.");
        } finally {
            writer.println("</body></html>");
        }
    }

    private LinkedList<Integer> extractDigits(int number) {
        LinkedList<Integer> digits = new LinkedList<>();
        if (number < 0) return digits;

        do {
            digits.addFirst(number % 10);
            number /= 10;
        } while (number > 0);

        return digits;
    }

    private void printErrorMessage(PrintWriter writer, String message) {
        writer.println("<p>" + message + "</p>");
    }
}

// http://localhost:8080/lab1_compulsory-1.0-SNAPSHOT/digits-servlet?number=1234