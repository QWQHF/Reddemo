package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudyBase {
    private Long id;
    private String name;
    private String mainImage;
    private Double rating;
    private String location;
    private String description;
    private Integer maxCapacity;
    private Boolean available;
    private String openTime;
    private Integer currentBookings;
    private String contact;
    private String notice;
    private Double latitude;
    private Double longitude;
    private String region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 关联属性
    private List<String> tags;
    private List<String> facilities;
    private List<String> images;
}