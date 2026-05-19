package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.TripDto.TripRequestDto;
import com.busticket.busticketbooking.dto.TripDto.TripResponseDto;

import java.util.List;

public interface TripService {

    List<TripResponseDto> getAllTrips();

    TripResponseDto getTripById(Integer id);

    TripResponseDto addTrip(TripRequestDto tripRequestDto);

    TripResponseDto updateTrip(Integer id,
                               TripRequestDto tripRequestDto);

    void closeTrip(Integer id);

    List<TripResponseDto> searchTrips(String fromCity,
                                      String toCity);

    Integer getAvailableSeats(Integer id);
}