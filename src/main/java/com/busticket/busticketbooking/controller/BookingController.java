package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(
            @RequestBody Booking booking) {

        return bookingService.createBooking(booking);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingById(
            @PathVariable Integer bookingId) {

        return bookingService.getBookingById(bookingId);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping("/{bookingId}")
    public Booking updateBooking(
            @PathVariable Integer bookingId,
            @RequestBody Booking booking) {

        return bookingService.updateBooking(
                bookingId,
                booking
        );
    }

    @DeleteMapping("/{bookingId}")
    public String deleteBooking(
            @PathVariable Integer bookingId) {

        bookingService.deleteBooking(bookingId);

        return "Booking deleted successfully";
    }
}