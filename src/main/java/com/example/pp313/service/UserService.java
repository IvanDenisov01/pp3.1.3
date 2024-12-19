package com.example.pp313.service;

import com.example.pp313.model.User;

import java.util.List;

public interface UserService {

    User getUserById(long id);

    void addUser(User user);

    void deleteUser(long id);

    List<User> getAllUsers();

    void updateUser(User user);

    User findByUsername(String username);

    void addDefaultUser();

    User passwordCoder(User user);
}
