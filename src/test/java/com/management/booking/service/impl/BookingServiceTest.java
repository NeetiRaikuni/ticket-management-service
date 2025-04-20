package com.management.booking.service.impl;

import com.management.booking.constant.SectionEnum;
import com.management.booking.entity.BookingEntity;
import com.management.booking.entity.UserDetailEntity;
import com.management.booking.model.Booking;
import com.management.booking.repository.BookingRepository;
import com.management.booking.repository.UserDetailRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserDetailRepository userDetailRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;
    private BookingEntity bookingEntity;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        UserDetailEntity user = new UserDetailEntity("john@example.com", "John", "Doe");

        booking = new Booking();
        booking.setUser(user);
        booking.setFromStation("NYC");
        booking.setToStation("Boston");
        booking.setPricePaid(BigDecimal.valueOf(99.99));

        bookingEntity = new BookingEntity();
        bookingEntity.setBookingId(1L);
        bookingEntity.setUserEmail("john@example.com");
        bookingEntity.setSection(SectionEnum.A);

    }

    @AfterEach
    public void destroy(){
        bookingService= null;
    }

    @Test
    public void testGetBookingForUser(){
        doReturn(bookingEntity).when(bookingRepository).findByUserEmail(any());
        BookingEntity entity= bookingService.getBookingForUser("john@example.com");
        Assertions.assertEquals(entity.getBookingId(), 1L);
        Assertions.assertEquals(entity.getUserEmail(), "john@example.com");
        Assertions.assertEquals(entity.getSection(), SectionEnum.A);
        verify(bookingRepository, times(1)).findByUserEmail(any());
    }

    @Test
    public void testGetBookingsForSection(){
        doReturn(Collections.singletonList(bookingEntity)).when(bookingRepository).findBySection(any());
        List<BookingEntity> entities= bookingService.getUsersBookedInSection(SectionEnum.A);
        BookingEntity entity=entities.get(0);
        Assertions.assertEquals(entity.getBookingId(), 1L);
        Assertions.assertEquals(entity.getUserEmail(), "john@example.com");
        Assertions.assertEquals(entity.getSection(), SectionEnum.A);
        verify(bookingRepository, times(1)).findBySection(any());
    }
}
