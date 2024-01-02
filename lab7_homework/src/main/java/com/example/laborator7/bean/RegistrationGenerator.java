package com.example.laborator7.bean;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.util.UUID;

@RequestScoped
public class RegistrationGenerator {

    @Produces
    @Named("registrationNumber")
    String getRegistrationNumber() {
        return UUID.randomUUID().toString();
    }

}
