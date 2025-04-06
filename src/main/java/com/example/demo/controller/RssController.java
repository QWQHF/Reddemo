package com.example.demo.controller;

import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultCode;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.service.RssService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rss")
public class RssController {
    private final RssService rssService;
    private static final String RSS_URL = "http://www.people.com.cn/rss/politics.xml";

    public RssController(RssService rssService) {
        this.rssService = rssService;
    }

    @GetMapping
    public ResponseBody<?> getRssAsJson() {
        try {
            String xml = rssService.fetchRssXml(RSS_URL);
            JSONObject json = rssService.parseRssToJson(xml);
            return ResultResponse.success(json.toString(2));
        } catch (Exception e) {
            return ResultResponse.failure(ResultCode.FAIL);
        }
    }
}

