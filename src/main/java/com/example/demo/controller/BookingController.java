package com.example.demo.controller;

import com.example.demo.dto.request.CreateBookingRequest;
import com.example.demo.dto.response.ResponseBody;
import com.example.demo.dto.response.ResultResponse;
import com.example.demo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// BookingController.java
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseBody<?> createBooking(@RequestBody CreateBookingRequest request) {
        return ResultResponse.success(bookingService.createBooking(request));
    }
}

