package com.example.demo.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BaseItem {
    private Long id;
    private String name;
    private String image;
    private Double rating;      // 评分（1-5分）
    private String location;    // 详细地址
    private List<String> tags;   // 标签（主题相关）
    private String description;
    private Integer maxCapacity; // 最大承载量
    private Boolean available;  // 是否可预约
    private String openTime;    // 开放时间（如"08:30-17:00"）
    private Integer currentBookings; // 当前预约数（可选）
}