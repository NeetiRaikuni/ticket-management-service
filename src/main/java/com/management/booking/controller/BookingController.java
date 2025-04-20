package com.management.booking.controller;

import com.management.booking.constant.SectionEnum;
import com.management.booking.model.Booking;
import com.management.booking.entity.BookingEntity;
import com.management.booking.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.management.booking.constant.BookingManagementConstants.*;

@RestController
@AllArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping(value = API_VERSION_1 + BOOKING)
    public BookingEntity createBooking(@RequestHeader(value = TRACE_ID) String traceId, @RequestBody Booking booking) {
        log.debug("Creating booking request for trace-id {}", traceId);
        return bookingService.createBooking(booking);
    }

    @GetMapping(value = USER_BOOKING_V1)
    public BookingEntity getBookingForUser(@RequestHeader(value = TRACE_ID) String traceId, @RequestParam String email) {
        log.debug("Getting booking details for trace-id {} and email {}", traceId, email);
        return bookingService.getBookingForUser(email);
    }

    @GetMapping(value = API_VERSION_1 + BOOKING + SECTION)
    public List<BookingEntity> getUserBookingDetailsForSection(@RequestHeader(value = TRACE_ID) String traceId, @RequestParam SectionEnum section) {
        log.debug("Getting booking details for trace-id {} and section {}", traceId, section);
        return bookingService.getUsersBookedInSection(section);
    }

    @DeleteMapping(value = USER_BOOKING_V1)
    public void deleteBookingForUser(@RequestHeader(value = TRACE_ID) String traceId, @RequestParam String email) {
        log.debug("Getting booking details for trace-id {} and email {}", traceId, email);
        bookingService.removeBooking(email);
    }

    @PutMapping(value = USER_BOOKING_V1)
    public BookingEntity modifyUserBooking(@RequestHeader(value = TRACE_ID) String traceId, @RequestParam String email) {
        log.debug("Modifying booking details for trace-id {} and email {}", traceId, email);
        return bookingService.modifyBooking(email);
    }
}
