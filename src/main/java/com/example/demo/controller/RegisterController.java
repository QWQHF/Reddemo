package com.example.demo.controller;

import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultCode;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {
     @Autowired
    private UserInfoService registerService;

    @PostMapping("/register")
    public ResponseBody<?> add(@RequestBody UserInfo userInfo){
        try{
            registerService.add(userInfo);
            return ResultResponse.success();
        }catch (Exception e){
            return ResultResponse.failure(ResultCode.FAIL);
        }

    }
}

