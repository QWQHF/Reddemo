package com.example.demo.mapper;

import com.example.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserInfoMapper {
    void add(UserInfo userInfo);
    void delete(Integer id);
    void update(UserInfo userInfo);
    UserInfo queryById(Integer id);
    List<UserInfo> queryAll();
}

