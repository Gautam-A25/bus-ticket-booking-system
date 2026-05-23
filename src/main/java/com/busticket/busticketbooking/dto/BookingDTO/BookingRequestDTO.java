package com.busticket.busticketbooking.dto.BookingDTO;

import com.busticket.busticketbooking.entity.Booking.BookingStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// DTO used to receive booking request data from client
public class BookingRequestDTO {

    // Stores seat number selected for booking
    @NotNull(message = "Seat number is required")

    // Ensures seat number is greater than 0
    @Min(value = 1, message = "Seat number must be greater than 0")
    private Integer seatNumber;

    // Stores current booking status
    @NotNull(message = "Booking status is required")
    private BookingStatus status;

    // Default constructor
    public BookingRequestDTO() {
    }

    // Parameterized constructor for object initialization
    public BookingRequestDTO(
            Integer seatNumber,
            BookingStatus status
    ) {
        this.seatNumber = seatNumber;
        this.status = status;
    }

    // Returns seat number value
    public Integer getSeatNumber() {
        return seatNumber;
    }

    // Sets seat number value
    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Returns booking status value
    public BookingStatus getStatus() {
        return status;
    }

    // Sets booking status value
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}