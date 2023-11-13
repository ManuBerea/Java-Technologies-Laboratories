package com.example.laborator4good.model;

import lombok.Data;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;
@ManagedBean
@ApplicationScoped
@Data
public class Project implements Serializable {

    private Long project_id;
    private String name;
    private String category;
    private String description;
    private Date deadline;

}
