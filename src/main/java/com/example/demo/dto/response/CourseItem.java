package com.example.demo.dto.response;

import lombok.Data;

@Data
public class CourseItem {
    private Long id;
    private String title;
    private String thumbnail;
    private String lecturerName;
    private String lecturerAvatar;
    private Integer duration;
    private Integer enrolled;
    private Double rating;
    private Integer progress;
    private String status;
    private Boolean isFeatured;
    private String type;
}
