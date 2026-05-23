package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDTO;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDTO;
import com.busticket.busticketbooking.dto.TripDTO.SeatAvailabilityDTO;

import java.time.LocalDate;
import java.util.List;

public interface TripService {

    List<TripResponseDTO> getAllTrips();

    TripResponseDTO getTripById(Integer id);

    TripResponseDTO addTrip(TripRequestDTO tripRequestDto);

    TripResponseDTO updateTrip(Integer id,
                               TripRequestDTO tripRequestDto);

    String closeTrip(Integer id);

    List<TripResponseDTO> searchTrips(String fromCity,
                                      String toCity);

    List<TripResponseDTO> searchTrips(String fromCity,
                                      String toCity,
                                      LocalDate date);

    List<SeatAvailabilityDTO> getSeatAvailability(Integer tripId);

    List<Integer> getBookedSeats(Integer tripId);

    List<Integer> getAvailableSeatList(Integer tripId);
    void deleteTrip(Integer id);
}