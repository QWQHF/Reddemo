package com.example.demo.entity;

import lombok.Data;

@Data
public class Login {
    private static String username;
    private static String password;
    private static String token;

    public static String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public static String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public static String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
