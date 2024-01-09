package com.example.laborator7.interceptor;

import com.example.laborator7.model.Timetable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {
    private static final String LOG_FILE_PATH = "C:\\Users\\manub\\Desktop\\Cursurile mele\\Master\\Anul I\\Tehnologii JAVA\\Lab7\\src\\main\\java\\com\\example\\laborator7\\submissions.txt";

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        System.out.println("Interceptor invoked for method: " + invocationContext.getMethod().getName());

        Object[] parameters = invocationContext.getParameters();

        for (Object parameter : parameters) {
            if (parameter instanceof Timetable) {
                Timetable timetable = (Timetable) parameter;
                logSubmission(timetable);
                break;  // we have only one Timetable parameter
            }
        }

        return invocationContext.proceed();
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
