package com.busticket.busticketbooking.dto.BookingDTO;

import com.busticket.busticketbooking.entity.Booking.BookingStatus;

// DTO used to send booking details as response
public class BookingResponseDTO {

    // Stores unique booking ID
    private Integer id;

    // Stores associated trip ID
    private Integer tripId;

    // Stores booked seat number
    private Integer seatNumber;

    // Stores current booking status
    private BookingStatus status;

    // Default constructor
    public BookingResponseDTO() {
    }

    // Parameterized constructor for object initialization
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

    // Returns booking ID
    public Integer getId() {
        return id;
    }

    // Sets booking ID
    public void setId(Integer id) {
        this.id = id;
    }

    // Returns trip ID
    public Integer getTripId() {
        return tripId;
    }

    // Sets trip ID
    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    // Returns booked seat number
    public Integer getSeatNumber() {
        return seatNumber;
    }

    // Sets booked seat number
    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Returns booking status
    public BookingStatus getStatus() {
        return status;
    }

    // Sets booking status
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}