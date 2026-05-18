package com.busticket.busticketbooking.dto.bookingDTO;

import com.busticket.busticketbooking.entity.Booking.BookingStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// DTO used for booking request data
public class BookingRequestDTO {

    // Seat number for booking
    @NotNull(message = "Seat number is required")
    @Min(value = 1, message = "Seat number must be greater than 0")
    private Integer seatNumber;

    // Booking status
    @NotNull(message = "Booking status is required")
    private BookingStatus status;

    // Default constructor
    public BookingRequestDTO() {
    }

    // Parameterized constructor
    public BookingRequestDTO(
            Integer seatNumber,
            BookingStatus status
    ) {
        this.seatNumber = seatNumber;
        this.status = status;
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