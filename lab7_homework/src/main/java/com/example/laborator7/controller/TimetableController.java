package com.example.laborator7.controller;

import com.example.laborator7.bean.TimetableBean;
import com.example.laborator7.interceptor.Logged;
import com.example.laborator7.model.Timetable;
import com.example.laborator7.model.User;
import lombok.Getter;
import lombok.Setter;
import com.example.laborator7.service.TimetableService;
import com.example.laborator7.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RequestScoped
@Named
@Getter
@Setter
public class TimetableController implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private TimetableService timetableService;

    @Inject
    private Instance<String> registrationNumberInstance;

    @Inject
    private TimetableBean timetableBean;

    private User getCurrentUser() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> cookiesMap = externalContext.getRequestCookieMap();

        if (cookiesMap.containsKey("user")) {
            Cookie userCookie = (Cookie) cookiesMap.get("user");
            String username = userCookie.getValue();
            return userService.findUserByUsername(username);
        }
        return null;
    }

    public void addTimeTable() {
        timetableBean.setUser(getCurrentUser());
        timetableBean.setRegistrationNumber(registrationNumberInstance.get());

        try {
            timetableService.createTimetable(timetableBean.convertToEntity());
        } catch (Exception e) {
            System.out.println("Error while adding timetable: " + e.getMessage());
        }
    }

    public List<Timetable> getAllTimetablePreferences() {
        return timetableService.getAllTimetables();
    }

}
