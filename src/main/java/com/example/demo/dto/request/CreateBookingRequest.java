package com.example.demo.dto.request;

import lombok.Data;

// CreateBookingRequest.java
@Data
public class CreateBookingRequest {
    private Long baseId;
    private String date;
    private Integer participants;
    private String contactName;
    private String contactPhone;
    private String remark;
}
