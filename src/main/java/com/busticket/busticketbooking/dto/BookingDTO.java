package com.busticket.busticketbooking.dto;

import com.busticket.busticketbooking.entity.Booking.BookingStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingDTO {

    private Integer id;

    @NotNull(message = "Trip ID is required for booking")
    private Integer tripId;

    @NotNull(message = "Seat number is required for booking")
    @Min(value = 1, message = "Seat number must be greater than 0")
    private Integer seatNumber;

    @NotNull(message = "Booking status is required")
    private BookingStatus status;

    public BookingDTO() {
    }

    public BookingDTO(Integer id, Integer tripId, Integer seatNumber, BookingStatus status) {
        this.id = id;
        this.tripId = tripId;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}