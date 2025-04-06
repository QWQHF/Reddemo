package com.example.demo.service;

import com.example.demo.entity.FilterOption;
import com.example.demo.entity.FilterOptions;
import com.example.demo.mapper.FilterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final FilterMapper filterMapper;

    public FilterOptions getFilterOptions() {
        FilterOptions options = new FilterOptions();
        options.setThemes(filterMapper.selectThemeOptions());
        options.setRegions(filterMapper.selectRegionOptions());
        options.setSortOptions(getStaticSortOptions());
        return options;
    }

    private List<FilterOption> getStaticSortOptions() {
        return Arrays.asList(
                new FilterOption("rating_desc", "评分从高到低"),
                new FilterOption("rating_asc", "评分从低到高"),
                new FilterOption("popularity_desc", "人气排序")
        );
    }
}
