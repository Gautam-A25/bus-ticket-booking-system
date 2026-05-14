package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Available', 'Booked') DEFAULT 'Available'")
    private BookingStatus status = BookingStatus.Available;

    public enum BookingStatus {
        Available, Booked
    }
}
