package com.example.demo.service;

import com.example.demo.dto.response.AvailabilityResponse;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.dto.request.CreateBookingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
// BookingService.java
public interface BookingService {
    BookingResponse createBooking(CreateBookingRequest request);
    AvailabilityResponse getAvailability(Long baseId, LocalDate date) throws Exception;
}

