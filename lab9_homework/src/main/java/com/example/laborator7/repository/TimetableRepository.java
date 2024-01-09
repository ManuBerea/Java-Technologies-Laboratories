package com.example.laborator7.repository;

import com.example.laborator7.model.Timetable;

import java.util.List;


public interface TimetableRepository {

    void createTimetable(Timetable timetable) throws Exception;

    void updateTimetable(Long id, Timetable timetable) throws Exception;

    void deleteTimetable(Long id) throws Exception;

    List<Timetable> getAllTimetables();

    Timetable findTimeslot(String dayOfWeek, String hourOfDay);

    List<Timetable> findTimetablesByUserId(Long userId);

}
