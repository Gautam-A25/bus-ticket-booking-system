package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.service.BookingService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    @Override
    public Booking getBookingById(Integer bookingId) {
        return bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with ID " + bookingId + " not found"));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public Booking updateBooking(Integer bookingId, Booking booking) {

        Booking existingBooking = getBookingById(bookingId);

        existingBooking.setTrip(booking.getTrip());
        existingBooking.setSeatNumber(booking.getSeatNumber());
        existingBooking.setStatus(booking.getStatus());

        return bookingRepo.save(existingBooking);
    }

    @Override
    public void deleteBooking(Integer bookingId) {
        bookingRepo.deleteById(bookingId);
    }
}