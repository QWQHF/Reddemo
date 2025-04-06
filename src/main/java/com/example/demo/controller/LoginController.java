package com.example.demo.controller;

import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultCode;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.entity.Login;
import com.example.demo.service.JwtUtil;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseBody<?> login(@RequestBody Login login){
        try{
            String foundUsername = loginService.findUsername(Login.getUsername());
            String foundPassword = loginService.findPassword(Login.getUsername());
            if (Login.getUsername() != null && Login.getPassword() != null && Login.getUsername().equals(foundUsername) && Login.getPassword().equals(foundPassword)) {
                String token = JwtUtil.generateToken(Login.getUsername());
                login.setToken(token);
                return ResultResponse.success(token);
            }
            else return ResultResponse.failure(ResultCode.UNAUTHORIZED);
        }catch (Exception e){
            return ResultResponse.failure(ResultCode.FAIL);
        }

    }
}
