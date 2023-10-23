package com.example.lab2.model;

import jakarta.servlet.http.Part;

public class Input {
    private Part graphFile;

    public void setGraphFile(Part graphFile) {
        this.graphFile = graphFile;
    }

    public Part getGraphFile() {
        return graphFile;
    }
}
