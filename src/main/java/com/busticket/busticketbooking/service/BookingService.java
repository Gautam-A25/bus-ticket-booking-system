package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    Booking getBookingById(Integer bookingId);

    List<Booking> getAllBookings();

    Booking updateBooking(Integer bookingId, Booking booking);

    void deleteBooking(Integer bookingId);
}