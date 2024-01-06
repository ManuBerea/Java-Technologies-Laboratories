package com.example.laborator7.repository.impl;

import com.example.laborator7.interceptor.Logged;
import com.example.laborator7.model.Timetable;
import com.example.laborator7.repository.TimetableRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Transactional
public class TimetableRepositoryImpl implements TimetableRepository {

    @PersistenceContext(name = "UniversityManagementPU")
    EntityManager em;

    private static final String LOG_FILE_PATH = "C:\\Users\\manub\\Desktop\\Cursurile mele\\Master\\Anul I\\Tehnologii JAVA\\Lab7\\src\\main\\java\\com\\example\\laborator7\\submissions.txt";

    @Override
    @Logged
    public void createTimetable(Timetable timetable) throws Exception {
        if (findTimeslot(timetable.getDayOfWeek(), timetable.getHourOfDay()) != null) {
            throw new Exception("Timeslot not available.");
        }
        em.persist(timetable);
        em.flush();
        em.refresh(timetable);

        logSubmission(timetable);
    }

    @Override
    public void updateTimetable(Long id, Timetable timetable) throws Exception {
        Timetable existingTimetable = em.find(Timetable.class, id);
        if (existingTimetable == null) {
            throw new Exception("Timetable not found for ID: " + id);
        }
        existingTimetable.setRegistrationNumber(timetable.getRegistrationNumber());
        existingTimetable.setTeacher(timetable.getTeacher());
        existingTimetable.setContent(timetable.getContent());
        existingTimetable.setDayOfWeek(timetable.getDayOfWeek());
        existingTimetable.setHourOfDay(timetable.getHourOfDay());
        em.merge(existingTimetable);
    }

    @Override
    public void deleteTimetable(Long id) throws Exception {
        Timetable timetable = em.find(Timetable.class, id);
        if (timetable == null) {
            throw new Exception("Timetable not found for ID: " + id);
        }
        em.remove(timetable);
    }

    @Override
    public List<Timetable> getAllTimetables() {
        return em.createNamedQuery("TimetablePreference.findAll", Timetable.class).getResultList();
    }

    @Override
    public Timetable findTimeslot(String dayOfWeek, String hourOfDay) {
        List<Timetable> list = em.createNamedQuery("TimetablePreference.findTimeslot", Timetable.class)
                .setParameter(1, dayOfWeek)
                .setParameter(2, hourOfDay)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Timetable> findTimetablesByUserId(Long userId) {
        TypedQuery<Timetable> query = em.createQuery("SELECT t FROM Timetable t WHERE t.teacher.userId = :userId", Timetable.class);
        query.setParameter("userId", userId.intValue());
        return query.getResultList();
    }

    private void logSubmission(Timetable timetable) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("Registration Number: " + timetable.getRegistrationNumber() +
                    ", Teacher: " + timetable.getTeacher().getUsername() +
                    ", Day: " + timetable.getDayOfWeek() +
                    ", Time Frame: " + timetable.getHourOfDay() +
                    ", Submission Time: " + new Date());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
