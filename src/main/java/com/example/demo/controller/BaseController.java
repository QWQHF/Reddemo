package com.example.demo.controller;
import com.example.demo.dto.request.GetBasesParams;
import com.example.demo.dto.response.BaseDetail;
import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultCode;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.service.BaseService;
import com.example.demo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bases")
public class BaseController {

    @Autowired
    private BaseService baseService;
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseBody<?> getBases(GetBasesParams params) {
        try {
            return ResultResponse.success(baseService.getBases(params));
        }catch (Exception e) {
            return ResultResponse.failure(ResultCode.FAIL);
        }
    }

    @GetMapping("/{id}")
    public ResponseBody<?> getBaseDetail(@PathVariable Long id) {
        return ResultResponse.success(baseService.getBaseDetail(id));
    }
    @GetMapping("/{id}/availability")
    public ResponseBody<?> getAvailability(@PathVariable Long id, @RequestParam(required = false) LocalDate date) throws Exception {
        return ResultResponse.success(bookingService.getAvailability(id, date));
    }

}