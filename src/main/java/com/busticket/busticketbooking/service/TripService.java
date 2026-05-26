package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDTO;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDTO;
import com.busticket.busticketbooking.dto.TripDTO.SeatAvailabilityDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface defining all trip management operations.
 *
 * <p>Trips are the core bookable unit in the system. Each trip links a {@code Route},
 * a {@code Bus}, two {@code Driver}s, boarding/dropping {@code Address}es,
 * departure/arrival times, fare, and available seat count.</p>
 */
public interface TripService {

    /** Returns all trips in the system. */
    List<TripResponseDTO> getAllTrips();

    /** Fetches a trip by its ID; throws if not found. */
    TripResponseDTO getTripById(Integer id);

    /** Creates and persists a new trip from the given request data. */
    TripResponseDTO addTrip(TripRequestDTO tripRequestDto);

    /** Updates all fields of an existing trip identified by {@code id}. */
    TripResponseDTO updateTrip(Integer id,
                               TripRequestDTO tripRequestDto);

    /** Sets available seats to 0, effectively closing the trip for new bookings. */
    String closeTrip(Integer id);

    /** Searches for trips between two cities (all dates). */
    List<TripResponseDTO> searchTrips(String fromCity,
                                      String toCity);

    /** Searches for trips between two cities on a specific date. */
    List<TripResponseDTO> searchTrips(String fromCity,
                                      String toCity,
                                      LocalDate date);

    /** Returns seat availability status (Available/Booked) for every seat on the bus. */
    List<SeatAvailabilityDTO> getSeatAvailability(Integer tripId);

    /** Returns the list of already-booked seat numbers for a trip. */
    List<Integer> getBookedSeats(Integer tripId);

    /** Returns the list of currently available (unbooked) seat numbers for a trip. */
    List<Integer> getAvailableSeatList(Integer tripId);

    /** Deletes a trip and cascades deletion to its bookings, payments, and reviews. */
    void deleteTrip(Integer id);
}