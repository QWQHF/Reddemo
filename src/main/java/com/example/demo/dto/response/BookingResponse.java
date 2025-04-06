package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

// BookingResponse.java
@Data
@Builder
public class BookingResponse {
    private String bookingId;
    private String status;
    private String createdAt;
}

