package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Booking.java
@Data
public class Booking {
    private String bookingId;
    private Long baseId;
    private Long userId;
    private LocalDate bookingDate;
    private Integer participants;
    private String contactName;
    private String contactPhone;
    private String remark;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

