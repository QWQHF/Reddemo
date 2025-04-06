package com.example.demo.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BaseListResponse {
    private Long total;       // 总记录数（用于分页）
    private List<BaseItem> list;    // 当前页数据
}