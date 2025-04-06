package com.example.demo.entity;

import lombok.Data;

import java.util.List;

// FilterOptions.java
@Data
public class FilterOptions {
    private List<FilterOption> themes;
    private List<FilterOption> regions;
    private List<FilterOption> sortOptions;
}
