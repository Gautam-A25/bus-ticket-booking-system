package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.entity.Bus;

import java.util.List;

public interface BusService {

    Bus registerBus(Integer officeId, Bus bus);

    List<Bus> getBusesByOffice(Integer officeId);

    Bus getBusById(Integer busId);

    Bus updateBus(Integer busId, Bus bus);

    void deleteBus(Integer busId);
}