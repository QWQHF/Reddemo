package com.example.demo.mapper;

import com.example.demo.entity.FilterOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FilterMapper {
    // 获取所有主题标签（去重）
    @Select("SELECT DISTINCT tag_name as value, tag_name as label FROM base_tag")
    List<FilterOption> selectThemeOptions();

    // 获取所有地区（去重）
    @Select("SELECT DISTINCT region as value, region as label FROM study_base WHERE region IS NOT NULL")
    List<FilterOption> selectRegionOptions();
}