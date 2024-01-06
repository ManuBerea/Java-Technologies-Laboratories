package com.example.laborator7.config;

import com.example.laborator7.service.TimetableSubmissionService;

import javax.enterprise.context.ApplicationScoped;

import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationScoped
public class ApplicationConfig extends Application {

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(TimetableSubmissionService.class);
    }

    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

}