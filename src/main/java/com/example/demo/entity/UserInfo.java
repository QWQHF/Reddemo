package com.example.demo.entity;

import lombok.Data;

@Data
public class UserInfo {
    private Integer id;
    private String username;
    private String password;
    private String authority;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }

}
