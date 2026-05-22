package com.busticket.busticketbooking.dto.TripDTO;

public class SeatAvailabilityDTO {
    private Integer seatNumber;
    private String status;

    public SeatAvailabilityDTO() {}

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
