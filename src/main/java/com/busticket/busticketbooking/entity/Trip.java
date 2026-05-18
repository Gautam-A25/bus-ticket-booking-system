package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Integer id;

    @NotNull(message = "Route is required")
    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @NotNull(message = "Bus is required")
    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @NotNull(message = "Boarding address is required")
    @ManyToOne
    @JoinColumn(name = "boarding_address_id", nullable = false)
    private Address boardingAddress;

    @NotNull(message = "Dropping address is required")
    @ManyToOne
    @JoinColumn(name = "dropping_address_id", nullable = false)
    private Address droppingAddress;

    @NotNull(message = "Departure time is required")
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @NotNull(message = "Driver 1 is required")
    @ManyToOne
    @JoinColumn(name = "driver1_driver_id", nullable = false)
    private Driver driver1;

    @NotNull(message = "Driver 2 is required")
    @ManyToOne
    @JoinColumn(name = "driver2_driver_id", nullable = false)
    private Driver driver2;

    @NotNull(message = "Available seats is required")
    @PositiveOrZero(message = "Available seats cannot be negative")
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Fare must have up to 8 integer digits and 2 decimal places")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fare;

    @NotNull(message = "Trip date is required")
    @Column(name = "trip_date", nullable = false)
    private LocalDateTime tripDate;

    public Trip() {
    }

    public Trip(Integer id, Route route, Bus bus, Address boardingAddress, Address droppingAddress,
                LocalDateTime departureTime, LocalDateTime arrivalTime, Driver driver1, Driver driver2,
                Integer availableSeats, BigDecimal fare, LocalDateTime tripDate) {
        this.id = id;
        this.route = route;
        this.bus = bus;
        this.boardingAddress = boardingAddress;
        this.droppingAddress = droppingAddress;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.driver1 = driver1;
        this.driver2 = driver2;
        this.availableSeats = availableSeats;
        this.fare = fare;
        this.tripDate = tripDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Address getBoardingAddress() {
        return boardingAddress;
    }

    public void setBoardingAddress(Address boardingAddress) {
        this.boardingAddress = boardingAddress;
    }

    public Address getDroppingAddress() {
        return droppingAddress;
    }

    public void setDroppingAddress(Address droppingAddress) {
        this.droppingAddress = droppingAddress;
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

    public Driver getDriver1() {
        return driver1;
    }

    public void setDriver1(Driver driver1) {
        this.driver1 = driver1;
    }

    public Driver getDriver2() {
        return driver2;
    }

    public void setDriver2(Driver driver2) {
        this.driver2 = driver2;
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

    @AssertTrue(message = "Arrival time must be after departure time")
    public boolean isArrivalAfterDeparture() {
        return departureTime == null || arrivalTime == null || arrivalTime.isAfter(departureTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id != null && id.equals(trip.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", routeId=" + (route != null ? route.getId() : null) +
                ", busId=" + (bus != null ? bus.getId() : null) +
                ", boardingAddressId=" + (boardingAddress != null ? boardingAddress.getId() : null) +
                ", droppingAddressId=" + (droppingAddress != null ? droppingAddress.getId() : null) +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", driver1Id=" + (driver1 != null ? driver1.getId() : null) +
                ", driver2Id=" + (driver2 != null ? driver2.getId() : null) +
                ", availableSeats=" + availableSeats +
                ", fare=" + fare +
                ", tripDate=" + tripDate +
                '}';
    }
}