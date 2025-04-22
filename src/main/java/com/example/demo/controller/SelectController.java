package com.example.demo.controller;

import com.example.demo.dto.response.*;
import com.example.demo.dto.response.ResponseBody;
import com.example.demo.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SelectController {

    @Autowired
    private SelectService selectService;

    /**
     * 搜索
     */
    @PostMapping("/select")
    public ResponseBody<?> select(String name) {
        List<BaseItem> baseItems = selectService.selectBaseByName(name);
        List<CourseItem> courseItems = selectService.selectCourseByName(name);
        SelectResult selectResult = new SelectResult(baseItems, courseItems);
        return ResultResponse.success(selectResult);
    }
}
