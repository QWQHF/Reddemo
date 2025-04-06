package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import java.util.List;


public interface UserInfoService {
    void add(UserInfo userInfo);
    void delete(Integer id);
    void update(UserInfo userInfo);
    UserInfo queryById(Integer id);
    List<UserInfo> queryAll();
}

