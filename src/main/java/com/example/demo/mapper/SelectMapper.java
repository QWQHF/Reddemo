package com.example.demo.mapper;

import com.example.demo.dto.response.BaseItem;
import com.example.demo.dto.response.CourseItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelectMapper {
    List<BaseItem> queryAllBase(String name);
    List<CourseItem> queryAllCourse(String name);
}
