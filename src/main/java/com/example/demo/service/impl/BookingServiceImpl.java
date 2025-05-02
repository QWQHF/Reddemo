package com.example.demo.service.impl;

import com.example.demo.dto.request.BookingDTO;
import com.example.demo.dto.response.AvailabilityResponse;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.entity.Booking;
import com.example.demo.entity.BookingStatus;
import com.example.demo.entity.StudyBase;
import com.example.demo.mapper.BaseMapper;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

// BookingServiceImpl.java
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Autowired
    private final BookingMapper bookingMapper;
    @Autowired
    private final BaseMapper baseMapper;

    /*
     * 创建预约
     */
    @Override
    @Transactional
    public BookingResponse createBooking(BookingDTO bookingDTO) {
        String bookingId = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        Booking booking = getBooking(bookingDTO, bookingId, createdAt, updatedAt);

        bookingMapper.insertBooking(booking);
        // 更新预约人数
        baseMapper.updateCurrentBookings(bookingDTO.getBaseId());
        // 显示基地信息
        StudyBase base = baseMapper.selectBaseById(bookingDTO.getBaseId());
        BookingResponse bookingResponse= new BookingResponse(booking, base);
        return bookingResponse;
    }
    /*
     * 取消预约
     */
    @Override
    public void deleteBookingByUserId(Long baseId, Long userId) {
        bookingMapper.deleteBookingById(baseId,userId);
    }


    private static Booking getBooking(BookingDTO bookingDTO, String bookingId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setBaseId(bookingDTO.getBaseId());
        booking.setUserId(bookingDTO.getUserId());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setParticipants(bookingDTO.getParticipants());
        booking.setContactName(bookingDTO.getContactName());
        booking.setContactPhone(bookingDTO.getContactPhone());
        booking.setRemark(bookingDTO.getRemark());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(createdAt);
        booking.setUpdatedAt(updatedAt);
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
