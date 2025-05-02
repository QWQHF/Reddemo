package com.example.demo.dto.response;

import com.example.demo.entity.Booking;
import com.example.demo.entity.StudyBase;
import lombok.Data;

@Data
public class BookingResponse {
    private Booking booking;
    private StudyBase base;

    public BookingResponse(Booking booking, StudyBase base) {
        this.booking = booking;
        this.base = base;
    }
}
