package com.management.booking.controller;

import com.management.booking.constant.SectionEnum;
import com.management.booking.entity.BookingEntity;
import com.management.booking.entity.UserDetailEntity;
import com.management.booking.exception.GlobalExceptionHandler;
import com.management.booking.model.Booking;
import com.management.booking.service.BookingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static com.management.booking.constant.BookingManagementConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookingControllerTest {

    private MockMvc mockMvc;

    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    private Booking booking;
    private BookingEntity bookingEntity;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        bookingController=new BookingController(bookingService);
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

        mockMvc= MockMvcBuilders.standaloneSetup(bookingController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @AfterEach
    public void destroy(){

        bookingController= null;
    }

    @Test
    void testGetBookingForUserBookingFound() throws Exception {
        doReturn(bookingEntity).when(bookingService).getBookingForUser(anyString());

        mockMvc.perform(get(USER_BOOKING_V1)
                        .header(TRACE_ID, "abc123")
                        .param("email", "john@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.userEmail").value("john@example.com"));

        verify(bookingService, times(1)).getBookingForUser(anyString());
    }

    @Test
    void testGetBookingForUserNoBooking() throws Exception {
        doReturn(null).when(bookingService).getBookingForUser(anyString());
        mockMvc.perform(get(USER_BOOKING_V1)
                        .header(TRACE_ID, "abc123")
                        .param("email", "john@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookingService, times(1)).getBookingForUser(anyString());
    }

    @Test
    void testGetBookingForSection() throws Exception {
        doReturn(Collections.singletonList(bookingEntity)).when(bookingService).getUsersBookedInSection(any());

        mockMvc.perform(get(API_VERSION_1 + BOOKING + SECTION)
                        .header(TRACE_ID, "abc123")
                        .param("section", "A")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookingService, times(1)).getUsersBookedInSection(any());
    }


}
