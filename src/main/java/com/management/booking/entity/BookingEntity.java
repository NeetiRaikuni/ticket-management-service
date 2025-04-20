package com.management.booking.entity;


import com.management.booking.constant.SectionEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity(name = "booking")
@Data
public class BookingEntity {
    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Column(name = "price_paid")
    private BigDecimal pricePaid;

    @Column(name = "section")
    private SectionEnum section;
    @Column(name="from_station")
    private String fromStation;
    @Column(name = "to_station")
    private String toStation;
    @Column(name = "user_email")
    private String userEmail;
}
