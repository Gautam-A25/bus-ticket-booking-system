package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BusDto;

import java.util.List;

public interface BusService {

    BusDto registerBus(Integer officeId, BusDto busDto);

    List<BusDto> getBusesByOffice(Integer officeId);

    BusDto getBusById(Integer busId);

    BusDto updateBus(Integer busId, BusDto busDto);

    void deleteBus(Integer busId);
}