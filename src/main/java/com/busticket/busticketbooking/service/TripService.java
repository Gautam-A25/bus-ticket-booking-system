package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDTO;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDTO;

import java.util.List;

public interface TripService {

    List<TripResponseDTO> getAllTrips();

    TripResponseDTO getTripById(Integer id);

    TripResponseDTO addTrip(TripRequestDTO tripRequestDto);

    TripResponseDTO updateTrip(Integer id,
                               TripRequestDTO tripRequestDto);

    void closeTrip(Integer id);

    List<TripResponseDTO> searchTrips(String fromCity,
                                      String toCity);

    Integer getAvailableSeats(Integer id);
}