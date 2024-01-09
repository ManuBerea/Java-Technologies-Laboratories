package com.example.laborator7.service;

import com.example.laborator7.interceptor.Logged;
import com.example.laborator7.model.User;
import com.example.laborator7.repository.UserRepository;

import javax.inject.Inject;
import java.io.Serializable;


public class UserService implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Logged
    public void createUser(User user) throws Exception {
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new Exception("Username already exists");
        }
        userRepository.createUser(user);
    }
//
//    public User loginUser(User user) throws Exception {
//        return userRepository.loginUser(user);
//    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

}
