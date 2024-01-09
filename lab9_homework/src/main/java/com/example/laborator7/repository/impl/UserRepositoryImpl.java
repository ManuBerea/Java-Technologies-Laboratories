package com.example.laborator7.repository.impl;

import com.example.laborator7.interceptor.Logged;
import com.example.laborator7.model.User;
import com.example.laborator7.repository.UserRepository;
import com.example.laborator7.util.SecurityUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext(name = "UniversityManagementPU")
    EntityManager em;

    @Override
    public void createUser(User user) throws Exception {
        if (findUserByUsername(user.getUsername()) != null) {
            throw new Exception("Username already exists");
        }
        // Hash the password before saving to database
        String hashedPassword = SecurityUtil.hashPassword(user.getPass());
        user.setPass(hashedPassword); // Set the hashed password

        em.persist(user);
        em.flush();
        em.refresh(user);
    }

//    @Override
//    public User loginUser(User user) throws Exception {
//        User foundUser = findUserByUsername(user.getUsername());
//
//        if (foundUser == null) {
//            throw new Exception("User doesn't exist");
//        }
//
//        if (!user.getPass().equals(foundUser.getPass())) {
//            throw new Exception("Wrong credentials");
//        }
//        return foundUser;
//    }

    @Override
    public User findUserByUsername(String username) {
        List<User> list = em.createNamedQuery("User.findByUsername", User.class)
                .setParameter(1, username)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

}
