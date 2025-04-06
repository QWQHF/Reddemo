package com.example.demo.controller;

import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FilterController {
    private final FilterService filterService;

    @GetMapping("/filters")
    public ResponseBody<?> getFilters() {
        return ResultResponse.success(filterService.getFilterOptions());
    }
}