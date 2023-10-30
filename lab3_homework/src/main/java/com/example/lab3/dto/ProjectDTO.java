package com.example.lab3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ProjectDTO {
    int id;
    String name;
    private String category;
    String description;
    private Date deadline;
}
