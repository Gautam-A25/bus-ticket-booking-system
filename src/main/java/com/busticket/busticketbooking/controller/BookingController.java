package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BookingDTO;
import com.busticket.busticketbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public BookingDTO createBooking(
            @Valid @RequestBody BookingDTO bookingDTO) {

        return bookingService.createBooking(bookingDTO);
    }

    @GetMapping
    public List<BookingDTO> getAllBookings() {

        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public BookingDTO getBookingById(@PathVariable Integer id) {

        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    public BookingDTO updateBooking(
            @PathVariable Integer id,
            @Valid @RequestBody BookingDTO bookingDTO) {

        return bookingService.updateBooking(id, bookingDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Integer id) {

        bookingService.deleteBooking(id);

        return "Booking deleted successfully";
    }
}