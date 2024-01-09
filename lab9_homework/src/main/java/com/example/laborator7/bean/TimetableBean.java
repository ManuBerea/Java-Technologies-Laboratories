package com.example.laborator7.bean;

import com.example.laborator7.model.Timetable;
import com.example.laborator7.model.User;
import lombok.Data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Named
@RequestScoped
public class TimetableBean {

    @NotNull
    private String registrationNumber;
    @NotNull
    private User user;
    @Size(min = 3, max=25, message = "The name should between 3 and 25 characters.")
    private String content;
    @NotEmpty
    private String dayOfWeek;
    @NotEmpty
    private String hourOfDay;

    public TimetableBean() {}

    public Timetable convertToEntity() {
        return new Timetable(this.registrationNumber, this.user, this.content, this.dayOfWeek, this.hourOfDay);
    }

}
