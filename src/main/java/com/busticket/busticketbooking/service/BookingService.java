package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BookingDTO;

import java.util.List;

public interface BookingService {

    BookingDTO createBooking(BookingDTO bookingDTO);

    List<BookingDTO> getAllBookings();

    BookingDTO getBookingById(Integer id);

    BookingDTO updateBooking(Integer id, BookingDTO bookingDTO);

    void deleteBooking(Integer id);
}