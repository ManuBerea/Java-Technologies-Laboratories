package com.example.laborator7.repository;

import com.example.laborator7.model.User;


public interface UserRepository {

    void createUser(User user) throws Exception;

    User loginUser(User user) throws Exception;

    User findUserByUsername(String username);

}