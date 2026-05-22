package com.busticket.busticketbooking.dto.ReportDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// DTO used to send trip occupancy report data
public class TripOccupancyReportResponseDTO {

    // Stores unique trip ID
    @NotNull(message = "Trip id is required")
    private Integer tripId;

    // Stores trip route details
    @NotBlank(message = "Route is required")
    private String route;

    // Stores total seating capacity of bus
    @NotNull(message = "Capacity is required")

    // Ensures capacity is greater than 0
    @Positive(message = "Capacity must be greater than 0")
    private Integer capacity;

    // Stores total booked seats count
    @NotNull(message = "Booked seats is required")
    private Long bookedSeats;

    // Stores total available seats count
    @NotNull(message = "Available seats is required")
    private Long availableSeats;

    // Stores occupancy percentage of trip
    @NotNull(message = "Occupancy percentage is required")
    private Double occupancyPercentage;

    // Default constructor
    public TripOccupancyReportResponseDTO() {
    }

    // Parameterized constructor for object initialization
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

    // Returns trip ID
    public Integer getTripId() {
        return tripId;
    }

    // Sets trip ID
    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    // Returns route details
    public String getRoute() {
        return route;
    }

    // Sets route details
    public void setRoute(String route) {
        this.route = route;
    }

    // Returns bus capacity
    public Integer getCapacity() {
        return capacity;
    }

    // Sets bus capacity
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    // Returns booked seats count
    public Long getBookedSeats() {
        return bookedSeats;
    }

    // Sets booked seats count
    public void setBookedSeats(Long bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    // Returns available seats count
    public Long getAvailableSeats() {
        return availableSeats;
    }

    // Sets available seats count
    public void setAvailableSeats(Long availableSeats) {
        this.availableSeats = availableSeats;
    }

    // Returns occupancy percentage
    public Double getOccupancyPercentage() {
        return occupancyPercentage;
    }

    // Sets occupancy percentage
    public void setOccupancyPercentage(Double occupancyPercentage) {
        this.occupancyPercentage = occupancyPercentage;
    }
}