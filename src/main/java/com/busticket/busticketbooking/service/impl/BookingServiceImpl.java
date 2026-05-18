package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.BookingDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.BookingService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TripRepo tripRepo;

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {

        Trip trip = tripRepo.findById(bookingDTO.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        Booking booking = new Booking();

        booking.setTrip(trip);
        booking.setSeatNumber(bookingDTO.getSeatNumber());
        booking.setStatus(bookingDTO.getStatus());

        Booking savedBooking = bookingRepo.save(booking);

        return mapToDTO(savedBooking);
    }

    @Override
    public List<BookingDTO> getAllBookings() {

        return bookingRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public BookingDTO getBookingById(Integer id) {

        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return mapToDTO(booking);
    }

    @Override
    public BookingDTO updateBooking(Integer id, BookingDTO bookingDTO) {

        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Trip trip = tripRepo.findById(bookingDTO.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        booking.setTrip(trip);
        booking.setSeatNumber(bookingDTO.getSeatNumber());
        booking.setStatus(bookingDTO.getStatus());

        Booking updatedBooking = bookingRepo.save(booking);

        return mapToDTO(updatedBooking);
    }

    @Override
    public void deleteBooking(Integer id) {

        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        bookingRepo.delete(booking);
    }

    private BookingDTO mapToDTO(Booking booking) {

        return new BookingDTO(
                booking.getId(),
                booking.getTrip().getId(),
                booking.getSeatNumber(),
                booking.getStatus()
        );
    }
}