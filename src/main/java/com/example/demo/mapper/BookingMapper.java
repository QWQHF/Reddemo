package com.example.demo.mapper;

import com.example.demo.entity.Booking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

// BookingMapper.java
@Mapper
public interface BookingMapper {
    void insertBooking(Booking booking);
    int sumConfirmedParticipantsByBaseIdAndDate(@Param("baseId") Long baseId,
                                                @Param("bookingDate") LocalDate date);
    void deleteBookingById(Long baseId,Long userId);
}