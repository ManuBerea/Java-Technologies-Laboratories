package com.example.laborator7.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "TimetablePreference.findAll", query = "SELECT t FROM Timetable t"),
        @NamedQuery(name = "TimetablePreference.findTimeslot", query = "SELECT t FROM Timetable t WHERE t.dayOfWeek = ?1 AND t.hourOfDay = ?2")
})
public class Timetable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;

    @OneToOne
    private User teacher;
    private String content;
    private String dayOfWeek;
    private String hourOfDay;

    public Timetable(String registrationNumber, User teacher, String content, String dayOfWeek, String hourOfDay) {
        this.registrationNumber = registrationNumber;
        this.teacher = teacher;
        this.content = content;
        this.dayOfWeek = dayOfWeek;
        this.hourOfDay = hourOfDay;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", teacher=" + teacher +
                ", content='" + content + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", hourOfDay='" + hourOfDay + '\'' +
                '}';
    }

}
