package com.example.demo.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SelectResult {
    private List<BaseItem> baseItems;
    private List<CourseItem> courseItems;

    public SelectResult(List<BaseItem> baseItems, List<CourseItem> courseItems) {
        this.baseItems = baseItems;
        this.courseItems = courseItems;
    }
}
