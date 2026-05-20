package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;

import java.util.List;

public interface BusService {

    BusResponseDTO createBus(BusRequestDTO dto);

    List<BusResponseDTO> getAllBuses();

    BusResponseDTO getBusById(Integer id);

    List<BusResponseDTO> getBusesByOffice(Integer officeId);

    BusResponseDTO updateBus(Integer busId,
                             BusRequestDTO dto);

    void deleteBus(Integer id);
}