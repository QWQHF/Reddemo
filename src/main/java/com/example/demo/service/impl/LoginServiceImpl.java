package com.example.demo.service.impl;

import com.example.demo.mapper.LoginMapper;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public String findUsername(String username) {
        return loginMapper.findUsername(username);
    }
    @Override
    public String findPassword(String username) {
        return loginMapper.findPassword(username);
    }
}
