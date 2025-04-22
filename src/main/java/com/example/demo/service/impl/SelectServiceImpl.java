package com.example.demo.service.impl;

import com.example.demo.dto.response.BaseItem;
import com.example.demo.dto.response.CourseItem;
import com.example.demo.mapper.SelectMapper;
import com.example.demo.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    private SelectMapper selectMapper;
    public List<BaseItem> selectBaseByName(String name) {
        return selectMapper.queryAllBase(name);
    }

    public List<CourseItem> selectCourseByName(String name) {
        return selectMapper.queryAllCourse(name);
    }
}
