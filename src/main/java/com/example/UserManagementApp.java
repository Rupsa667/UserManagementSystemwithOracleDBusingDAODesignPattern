package com.example;

import com.example.dao.UserDao;
import com.example.dao.impl.UserDAOImpl;
import com.example.model.User;

import java.sql.SQLException;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class UserManagementApp {
    static Scanner sc = new Scanner(System.in);
    static UserDao us = new UserDAOImpl();
    static UserManagementApp u=new UserManagementApp();
    public static void main(String[] args) {
        int choice;
        while (true) {
            System.out.println("\n===== User Management System =====");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. Get User By ID");
            System.out.println("5. Get All Users");
            System.out.println("6. Search By Name");
            System.out.println("7. Search By Email");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == 8) {
                IO.println("exit");
                exit(0);
            }

            try {
                switch (choice) {
                    case 1 -> u.addUser();
                    case 2 -> u.updateUser();
                    case 3 -> u.deleteUser();
                    case 4 -> u.getUserById();
                    case 5 -> u.getAllUsers();
                    case 6 -> u.searchByName();
                    case 7 -> u.searchByEmail();
                    default -> System.out.println("Invalid choice!");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }
    public void addUser() throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        us.addUser(new User(name,email,age));
    }
    public  static void updateUser() throws SQLException {
        System.out.print("Enter id:");
        int id=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();
        System.out.print("Enter new age: ");
        int age = sc.nextInt();
        us.updateUser(new User(id,name,email,age));
    }
public static void deleteUser() throws SQLException {
    System.out.print("Enter user ID: ");
    int id = sc.nextInt();
    us.deleteUser(id);
}
    public static void getAllUsers() throws SQLException {
        us.getAllUsers().forEach(System.out::println);
    }
    public static void getUserById() throws SQLException {
        System.out.print("Enter user ID: ");
        int id = sc.nextInt();
        System.out.println("user details are:"+us.getUserById(id));
    }
    public static void searchByName() throws SQLException{
        System.out.print("Enter name keyword: ");
        String name = sc.nextLine();
        us.getUsersByName(name).forEach(System.out::println);
    }
    public static void searchByEmail() throws SQLException {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        IO.println("user details are:"+us.getUsersByEmail(email));
    }
}
