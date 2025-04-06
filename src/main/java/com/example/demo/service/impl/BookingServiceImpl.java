package com.example.demo.service.impl;

import com.example.demo.dto.response.AvailabilityResponse;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.dto.request.CreateBookingRequest;
import com.example.demo.entity.Booking;
import com.example.demo.entity.BookingStatus;
import com.example.demo.entity.StudyBase;
import com.example.demo.mapper.BaseMapper;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

// BookingServiceImpl.java
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final BaseMapper baseMapper;

    @Override
    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request) {
        String bookingId = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();

        Booking booking = getBooking(request, bookingId, createdAt);

        bookingMapper.insertBooking(booking);

        return BookingResponse.builder()
                .bookingId(bookingId)
                .status(booking.getStatus().name().toLowerCase())
                .createdAt(createdAt.toString())
                .build();
    }

    private static Booking getBooking(CreateBookingRequest request, String bookingId, LocalDateTime createdAt) {
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setBaseId(request.getBaseId());
        booking.setBookingDate(LocalDate.parse(request.getDate()));
        booking.setParticipants(request.getParticipants());
        booking.setContactName(request.getContactName());
        booking.setContactPhone(request.getContactPhone());
        booking.setRemark(request.getRemark());
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(createdAt);
        return booking;
    }
    @Override
    public AvailabilityResponse getAvailability(Long baseId, LocalDate date) throws Exception {
        // 1. 参数校验
        if (baseId == null) {
            throw new IllegalArgumentException("基地ID不能为空");
        }
        date = (date != null) ? date : LocalDate.now();

        // 2. 查询基地信息
        StudyBase base = baseMapper.selectBaseById(baseId);
        if (base == null) {
            throw new Exception("基地不存在: " + baseId);
        }

        // 3. 检查最大容量是否设置
        if (base.getMaxCapacity() == null) {
            throw new Exception("基地未设置最大容量");
        }

        // 4. 计算已预约人数（处理null情况）
        Integer booked = bookingMapper.sumConfirmedParticipantsByBaseIdAndDate(baseId, date);
        booked = (booked != null) ? booked : 0;

        // 5. 返回响应
        return new AvailabilityResponse(
                base.getMaxCapacity(),
                booked,
                base.getMaxCapacity() - booked
        );
    }
}
