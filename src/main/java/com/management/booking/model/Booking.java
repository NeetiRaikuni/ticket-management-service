package com.management.booking.model;

import com.management.booking.entity.UserDetailEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Booking {
    private UserDetailEntity user;
    private String fromStation;
    private String toStation;
    private BigDecimal pricePaid;
}
