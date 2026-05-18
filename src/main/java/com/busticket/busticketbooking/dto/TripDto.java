package com.busticket.busticketbooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TripDto {

    private Integer id;

    private Integer routeId;

    private Integer busId;

    private Integer boardingAddressId;

    private Integer droppingAddressId;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Integer driver1Id;

    private Integer driver2Id;

    private Integer availableSeats;

    private BigDecimal fare;

    private LocalDateTime tripDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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