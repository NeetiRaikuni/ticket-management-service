package com.management.booking.repository;

import com.management.booking.constant.SectionEnum;
import com.management.booking.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {


    BookingEntity findByUserEmail(String email);
    List<BookingEntity> findBySection(SectionEnum sectionEnum);
}
