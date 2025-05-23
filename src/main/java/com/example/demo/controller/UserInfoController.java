package com.example.demo.controller;

import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultCode;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.JwtUtil;
import com.example.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping("/users")
    @CrossOrigin
        public ResponseBody<?> queryAll(){
        try{
            return ResultResponse.success(userInfoService.queryAll());
        }catch (Exception e){
            return ResultResponse.failure(ResultCode.FAIL);
        }
    }
    /**
     * 根据token查询用户id
     * @param token
     * @return
     */
    @GetMapping
    @CrossOrigin
    public ResponseBody<?> queryById(String token){
        Long userId = JwtUtil.extractUserId(token);
        return ResultResponse.success(userId);
    }
}
