package com.example.demo.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class GetBasesParams {
    private String keyword;      // 搜索关键词（名称/地点）
    private List<String> themes; // 主题筛选（多选）
    private String region;       // 地区筛选（单选）
    private String sort;         // 排序方式
    private Integer page = 1;    // 当前页码（默认1）
    private Integer pageSize = 8;// 每页数量（默认8）
}