package com.management.booking.service.impl;

import com.management.booking.constant.SectionEnum;
import com.management.booking.entity.UserDetailEntity;
import com.management.booking.model.Booking;
import com.management.booking.repository.UserDetailRepository;
import com.management.booking.entity.BookingEntity;
import com.management.booking.repository.BookingRepository;
import com.management.booking.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserDetailRepository userDetailRepository;
    /**
     * This method takes a booking request and creates the booking for the user.
     *
     * @param booking - request for booking a seat.
     * @return Booking entity response with booking id.
     */
    @Override
    @Transactional
    public BookingEntity createBooking(Booking booking) {
        // save/update the user details
        UserDetailEntity userDetailEntity= new UserDetailEntity(booking.getUser().getEmail(), booking.getUser().getFirstName(), booking.getUser().getLastName());
        userDetailRepository.save(userDetailEntity);
        //create booking entity
        BookingEntity bookingEntity= new BookingEntity();
        bookingEntity.setFromStation(booking.getFromStation());
        bookingEntity.setToStation(booking.getToStation());
        bookingEntity.setPricePaid(booking.getPricePaid());
        //following code is used just to randomize the section. Assumption is that the sections have unlimited seats.
        //trying to assign seat number as per location would lead to race condition if multiple users try to book simultaneously.
        List<SectionEnum> allAvailableSections=Arrays.asList(SectionEnum.values());
        Collections.shuffle(allAvailableSections);
        bookingEntity.setSection(allAvailableSections.get(0));
        bookingEntity.setUserEmail(userDetailEntity.getEmail());
        bookingRepository.save(bookingEntity);
        return bookingEntity;
    }

    /**
     * This method takes the user email and returns the booking for the user.
     * @param email- email to search the bookings for.
     * @return Booking entity response with booking id.
     */
    @Override
    public BookingEntity getBookingForUser(String email) {
       return  bookingRepository.findByUserEmail(email);
    }

    /**
     * This method takes the section and returns the list of bookings with user details.
     *
     * @param section - train section.
     * @return list of bookings with user details.
     */
    @Override
    public List<BookingEntity> getUsersBookedInSection(SectionEnum section) {
        return bookingRepository.findBySection(section);
    }

    /**
     * This method is used to modify the booking for the user id passed.
     *
     * @param email - user id/email for which the booking should be modified.
     * @return modified booking
     */
    @Override
    @Transactional
    public BookingEntity modifyBooking(String email) {
        BookingEntity existingBooking= bookingRepository.findByUserEmail(email);
        if(SectionEnum.A.equals(existingBooking.getSection())){
            existingBooking.setSection(SectionEnum.B);
        }
        else{
            existingBooking.setSection(SectionEnum.A);
        }
        bookingRepository.save(existingBooking);
        return existingBooking;
    }

    /**
     * This method takes the user email and removes the booking.
     *
     * @param email - user id/email
     */
    @Override
    public void removeBooking(String email) {
        BookingEntity existingBooking= bookingRepository.findByUserEmail(email);
        bookingRepository.delete(existingBooking);
    }
}
