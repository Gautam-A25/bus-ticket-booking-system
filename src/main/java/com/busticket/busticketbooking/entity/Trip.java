package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "boarding_address_id", nullable = false)
    private Address boardingAddress;

    @ManyToOne
    @JoinColumn(name = "dropping_address_id", nullable = false)
    private Address droppingAddress;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "driver1_driver_id", nullable = false)
    private Driver driver1;

    @ManyToOne
    @JoinColumn(name = "driver2_driver_id", nullable = false)
    private Driver driver2;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(nullable = false)
    private BigDecimal fare;

    @Column(name = "trip_date", nullable = false)
    private LocalDateTime tripDate;
}
