package com.busticket.busticketbooking.dto.TripDTO;

/**
 * Data Transfer Object representing the availability status of a seat.
 * Used to send status lists ("Available" / "Booked") back to the client.
 */
public class SeatAvailabilityDTO {

    /** The seat number. */
    private Integer seatNumber;

    /** The status of the seat, e.g. "Available" or "Booked". */
    private String status;

    /**
     * Default no-argument constructor.
     */
    public SeatAvailabilityDTO() {}

    /**
     * Parameterized constructor to fully initialize the DTO.
     *
     * @param seatNumber the seat number
     * @param status     the availability status
     */
    public SeatAvailabilityDTO(Integer seatNumber, String status) {
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
