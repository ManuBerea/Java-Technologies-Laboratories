package com.example.laborator7.bean;

import com.example.laborator7.model.User;
import lombok.Data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Named
@RequestScoped
public class UserBean {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @Size(min = 3, max=25, message = "The name should between 3 and 25 characters.")
    private String password;

    private boolean admin;

    public UserBean() {
    }

    public UserBean(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.admin = isAdmin;
    }

    public User convertToEntity() {
        return new User(this.username, this.password, this.admin);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", pass='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }

}
