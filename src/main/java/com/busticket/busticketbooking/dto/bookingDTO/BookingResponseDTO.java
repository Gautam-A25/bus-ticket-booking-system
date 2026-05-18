package com.busticket.busticketbooking.dto.bookingDTO;

import com.busticket.busticketbooking.entity.Booking.BookingStatus;

// DTO used for booking response data
public class BookingResponseDTO {

    // Booking ID
    private Integer id;

    // Trip ID associated with booking
    private Integer tripId;

    // Booked seat number
    private Integer seatNumber;

    // Booking status
    private BookingStatus status;

    // Default constructor
    public BookingResponseDTO() {
    }

    // Parameterized constructor
    public BookingResponseDTO(
            Integer id,
            Integer tripId,
            Integer seatNumber,
            BookingStatus status
    ) {
        this.id = id;
        this.tripId = tripId;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    // Getter for booking ID
    public Integer getId() {
        return id;
    }

    // Setter for booking ID
    public void setId(Integer id) {
        this.id = id;
    }

    // Getter for trip ID
    public Integer getTripId() {
        return tripId;
    }

    // Setter for trip ID
    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    // Getter for seat number
    public Integer getSeatNumber() {
        return seatNumber;
    }

    // Setter for seat number
    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Getter for booking status
    public BookingStatus getStatus() {
        return status;
    }

    // Setter for booking status
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}