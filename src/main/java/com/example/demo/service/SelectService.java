package com.example.demo.service;

import com.example.demo.dto.response.BaseItem;
import com.example.demo.dto.response.CourseItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SelectService {
    List<BaseItem> selectBaseByName(String name);
    List<CourseItem> selectCourseByName(String name);
}
