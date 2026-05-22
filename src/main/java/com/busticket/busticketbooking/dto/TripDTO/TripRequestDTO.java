package com.busticket.busticketbooking.dto.TripDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * DTO used for creating and updating trips.
 * Contains validation rules for trip data.
 */
public class TripRequestDTO {

    @NotNull
    private Integer routeId;

    @NotNull
    private Integer busId;

    @NotNull
    private Integer boardingAddressId;

    @NotNull
    private Integer droppingAddressId;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    private Integer driver1Id;

    @NotNull
    private Integer driver2Id;

    @Positive
    private Integer availableSeats;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal fare;

    @NotNull
    private LocalDateTime tripDate;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getBoardingAddressId() {
        return boardingAddressId;
    }

    public void setBoardingAddressId(Integer boardingAddressId) {
        this.boardingAddressId = boardingAddressId;
    }

    public Integer getDroppingAddressId() {
        return droppingAddressId;
    }

    public void setDroppingAddressId(Integer droppingAddressId) {
        this.droppingAddressId = droppingAddressId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getDriver1Id() {
        return driver1Id;
    }

    public void setDriver1Id(Integer driver1Id) {
        this.driver1Id = driver1Id;
    }

    public Integer getDriver2Id() {
        return driver2Id;
    }

    public void setDriver2Id(Integer driver2Id) {
        this.driver2Id = driver2Id;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public LocalDateTime getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDateTime tripDate) {
        this.tripDate = tripDate;
    }
}