package com.example.demo.controller;

import com.example.demo.dto.request.BookingDTO;
import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// BookingController.java
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking/{baseId}")
public class BookingController {
    @Autowired
    private final BookingService bookingService;

    /*
    创建预约
     */
    @PostMapping("/create")
    public ResponseBody<?> createBooking(@RequestBody BookingDTO bookingDTO,  @PathVariable Long baseId) {
        bookingDTO.setBaseId(baseId);
        return ResultResponse.success(bookingService.createBooking(bookingDTO));
    }
    /*
    取消预约
     */
    @DeleteMapping
    public ResponseBody<?> deleteBookingByUserId(@PathVariable Long baseId, Long userId) {
        bookingService.deleteBookingByUserId(baseId, userId);
        return ResultResponse.success();
    }
}

