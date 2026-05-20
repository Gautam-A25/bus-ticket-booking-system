package com.busticket.busticketbooking.dto.ReportDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TripOccupancyReportResponseDTO {

    @NotNull(message = "Trip id is required")
    private Integer tripId;

    @NotBlank(message = "Route is required")
    private String route;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    private Integer capacity;

    @NotNull(message = "Booked seats is required")
    private Long bookedSeats;

    @NotNull(message = "Available seats is required")
    private Long availableSeats;

    @NotNull(message = "Occupancy percentage is required")
    private Double occupancyPercentage;

    public TripOccupancyReportResponseDTO() {
    }

    public TripOccupancyReportResponseDTO(Integer tripId,
                                          String route,
                                          Integer capacity,
                                          Long bookedSeats,
                                          Long availableSeats,
                                          Double occupancyPercentage) {
        this.tripId = tripId;
        this.route = route;
        this.capacity = capacity;
        this.bookedSeats = bookedSeats;
        this.availableSeats = availableSeats;
        this.occupancyPercentage = occupancyPercentage;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(Long bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public Long getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Long availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Double getOccupancyPercentage() {
        return occupancyPercentage;
    }

    public void setOccupancyPercentage(Double occupancyPercentage) {
        this.occupancyPercentage = occupancyPercentage;
    }
}