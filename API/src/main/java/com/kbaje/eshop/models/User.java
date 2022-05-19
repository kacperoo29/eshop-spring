package com.kbaje.eshop.models;

import java.util.LinkedList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User extends BaseEntity {
    
    private String email;
    private String password;
    private Iterable<String> roles;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Iterable<String> getRoles() {
        return roles;
    }

    protected User() {
        this.roles = new LinkedList<>();
    }

    protected User(String email, String password) {
        super();

        this.email = email;
        this.password = password;
        this.roles = new LinkedList<>();
    }

    public static User Create(String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return new User(email, encoder.encode(password));
    }

}
