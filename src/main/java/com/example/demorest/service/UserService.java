package com.example.demorest.service;

import com.example.demorest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> findById(int id);

    User addUser(User user);

    Optional<User> changeUser(int id, User user);

    void deleteById(int id);

    Optional<User> findByEmail(String email);


}
