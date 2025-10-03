package com.example.dao;

import com.example.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void addUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
    User getUserById(int id) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    // Additional Search Methods
    List<User> getUsersByName(String name) throws SQLException;
    User getUsersByEmail(String email) throws SQLException;
}
