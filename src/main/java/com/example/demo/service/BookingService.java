package com.example.demo.service;

import com.example.demo.dto.request.BookingDTO;
import com.example.demo.dto.response.AvailabilityResponse;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.entity.Booking;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
// BookingService.java
public interface BookingService {
    BookingResponse createBooking(BookingDTO bookingDTO);
    AvailabilityResponse getAvailability(Long baseId, LocalDate date) throws Exception;
    void deleteBookingByUserId(Long baseId, Long userId);
}

