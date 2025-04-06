package com.example.demo.dto.response;

import lombok.Data;

// AvailabilityResponse.java
@Data
public class AvailabilityResponse {
    private Integer total;
    private Integer booked;
    private Integer remaining;
    // 在 AvailabilityResponse 类中添加
    public AvailabilityResponse(Integer total, Integer booked, Integer remaining) {
        this.total = total;
        this.booked = booked;
        this.remaining = remaining;
    }
}
