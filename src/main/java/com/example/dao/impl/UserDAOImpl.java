package com.example.dao.impl;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.util.DBConnection;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDao {
    @Override

    public void addUser(User user) throws SQLException{
        try(Connection con= DBConnection.makecon()) {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("insert into users(name, email, age) values(?,?,?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getAge());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected ==1) {
                con.commit();
                System.out.println("User added successfully!" + rowsAffected);
            }
            else
                con.rollback();
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        try(Connection con= DBConnection.makecon()) {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("update users set name=?,email=?,age=? where id=?");
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getAge());
            ps.setInt(4, user.getId());
            int rowsAfeected = ps.executeUpdate();
            if (rowsAfeected > 0) {
                con.commit();
                IO.println("User update successfully!" + rowsAfeected);
            } else {
                con.rollback();
                IO.println("User update failed!" + rowsAfeected);
            }
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        try(Connection con= DBConnection.makecon()) {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("delete from users where id=?");
            ps.setInt(1, id);
            int rowsAfeected = ps.executeUpdate();
            if (rowsAfeected > 0) {
                con.commit();
                IO.println("User deleted successfully!" + rowsAfeected);
            } else {
                con.rollback();
                IO.println("User delete failed!" + rowsAfeected);
            }
        }
    }

    @Override
    public User getUserById(int id) throws SQLException {
        try(Connection con= DBConnection.makecon()) {
            PreparedStatement ps = con.prepareStatement("select * from users where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getInt("age"));
            }
            else {
                IO.println("User not found!" + id);
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        try(Connection con= DBConnection.makecon()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from users");
            IO.println("Show all users");
            while(rs.next()){
               list.add( new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getInt("age")));
            }
        }
        return list;
    }

    @Override
    public List<User> getUsersByName(String name) throws SQLException {
        List<User> list = new ArrayList<>();
        try(Connection con= DBConnection.makecon()) {
            PreparedStatement ps = con.prepareStatement("select * from users where lower(name) like ?");
            ps.setString(1, "%"+name.toLowerCase()+"%");
            ResultSet rs = ps.executeQuery();
            IO.println("Show all users");
            while(rs.next()){
                list.add( new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getInt("age")));
            }
        }
        return list;
    }

    @Override
    public User getUsersByEmail(String email) throws SQLException {
        List<User> list = new ArrayList<>();
        try(Connection con= DBConnection.makecon()) {
            PreparedStatement ps = con.prepareStatement("select * from users where email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            IO.println("Show all users");
            if(rs.next()){
                return( new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getInt("age")));
            }
        }
        return null;
    }
}
