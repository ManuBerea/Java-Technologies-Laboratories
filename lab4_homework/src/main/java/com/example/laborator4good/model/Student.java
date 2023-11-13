package com.example.laborator4good.model;


import lombok.Data;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
@Data
public class Student implements Serializable {

    private Integer student_id;
    private String name;

}