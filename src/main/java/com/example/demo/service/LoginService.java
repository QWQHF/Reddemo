package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String findUsername(String username);
    String findPassword(String username);
}
