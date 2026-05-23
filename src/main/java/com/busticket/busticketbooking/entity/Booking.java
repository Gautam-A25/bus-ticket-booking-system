package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

// Entity class mapped to bookings table
@Entity

// Specifies database table name
@Table(name = "bookings")
public class Booking {

    // Primary key of bookings table
    @Id

    // Auto-generates booking ID values
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer id;

    // Many bookings can belong to one trip
    @ManyToOne

    // Foreign key column for trip reference
    @JoinColumn(name = "trip_id")
    private Trip trip;

    // Stores booked seat number
    @Positive(message = "Seat number must be greater than 0")
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    // Stores booking status as String in database
    @Enumerated(EnumType.STRING)

    // Defines ENUM values and default value in database
    @Column(columnDefinition = "ENUM('Available', 'Booked') DEFAULT 'Available'")
    private BookingStatus status = BookingStatus.Available;

    // Default constructor
    public Booking() {
    }

    // Parameterized constructor for object initialization
    public Booking(Integer id, Trip trip, Integer seatNumber, BookingStatus status) {
        this.id = id;
        this.trip = trip;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    // Returns booking ID
    public Integer getId() {
        return id;
    }

    // Sets booking ID
    public void setId(Integer id) {
        this.id = id;
    }

    // Returns associated trip object
    public Trip getTrip() {
        return trip;
    }

    // Sets associated trip object
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    // Returns seat number
    public Integer getSeatNumber() {
        return seatNumber;
    }

    // Sets seat number
    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Returns booking status
    public BookingStatus getStatus() {
        return status;
    }

    // Sets booking status
    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    // Enum used to define booking status values
    public enum BookingStatus {
        Available, Booked
    }

    // Compares booking objects using booking ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id != null && id.equals(booking.id);
    }

    // Generates hash code for booking object
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Returns booking object details as String
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", tripId=" + (trip != null ? trip.getId() : null) +
                ", seatNumber=" + seatNumber +
                ", status=" + status +
                '}';
    }
}