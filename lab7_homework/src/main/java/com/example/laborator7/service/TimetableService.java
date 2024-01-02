package com.example.laborator7.service;

import com.example.laborator7.interceptor.Logged;
import com.example.laborator7.model.Timetable;
import com.example.laborator7.repository.TimetableRepository;

import javax.inject.Inject;
import java.util.List;


public class TimetableService {

    @Inject
    private TimetableRepository timetableRepository;

    @Logged
    public void createTimetable(Timetable timetable) throws Exception {
        timetableRepository.createTimetable(timetable);
    }

    public List<Timetable> getAllTimetables() {
        return timetableRepository.getAllTimetables();
    }

}
