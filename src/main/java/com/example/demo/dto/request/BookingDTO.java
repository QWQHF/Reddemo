package com.example.demo.dto.request;

import com.example.demo.entity.BookingStatus;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.demo.entity.BookingStatus.CONFIRMED;

// CreateBookingRequest.java
@Data
public class BookingDTO {
    private Long bookingId;
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
    // 插入前自动执行
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = BookingStatus.CONFIRMED;
    }
}