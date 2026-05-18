package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BusDto.BusRequestDto;
import com.busticket.busticketbooking.dto.BusDto.BusResponseDto;

import java.util.List;

public interface BusService {

    BusResponseDto createBus(BusRequestDto dto);

    List<BusResponseDto> getAllBuses();

    BusResponseDto getBusById(Integer id);

    List<BusResponseDto> getBusesByOffice(Integer officeId);

    BusResponseDto updateBus(Integer busId,
                             BusRequestDto dto);

    void deleteBus(Integer id);
}