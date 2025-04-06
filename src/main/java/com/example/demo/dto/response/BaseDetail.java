package com.example.demo.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDetail extends BaseItem {
    private String contact;       // 联系方式
    private List<String> facilities;  // 配套设施
    private String notice;        // 预约须知
    private List<String> images;      // 多图展示
    private Coordinate coordinate;         // 地理坐标
}

