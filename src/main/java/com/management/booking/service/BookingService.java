package com.management.booking.service;

import com.management.booking.constant.SectionEnum;
import com.management.booking.model.Booking;
import com.management.booking.entity.BookingEntity;

import java.util.List;

public interface BookingService {
    /**
     * This method takes a booking request and creates the booking for the user.
     * @param booking- request for booking a seat.
     * @return Booking entity response with booking id.
     */
    BookingEntity createBooking(Booking booking);

    /**
     * This method takes the user email and returns the booking for the user.
     * @param email- email to search the bookings for.
     * @return Booking entity response with booking id.
     */
    BookingEntity getBookingForUser(String email);

    /**
     * This method takes the section and returns the list of bookings with user details.
     * @param section- train section.
     * @return list of bookings with user details.
     */
    List<BookingEntity> getUsersBookedInSection(SectionEnum section);

    /**
     * This method is used to modify the booking for the user id passed.
     * @param email- user id/email for which the booking should be modified.
     * @return modified booking
     */
    BookingEntity modifyBooking(String email);

    /**
     * This method takes the user email and removes the booking.
     * @param email- user id/email
     */
    void removeBooking(String email);
}
