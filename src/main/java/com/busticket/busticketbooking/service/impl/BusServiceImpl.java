package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.service.BusService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepo busRepo;

    public BusServiceImpl(BusRepo busRepo) {
        this.busRepo = busRepo;
    }

    @Override
    public Bus registerBus(Integer officeId, Bus bus) {

        return busRepo.save(bus);
    }

    @Override
    public List<Bus> getBusesByOffice(Integer officeId) {

        return busRepo.findByOffice_id(officeId);
    }

    @Override
    public Bus getBusById(Integer busId) {

        return busRepo.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus with ID " + busId + " not found"));
    }

    @Override
    public Bus updateBus(Integer busId, Bus bus) {

        Bus existingBus = getBusById(busId);

        existingBus.setRegistrationNumber(bus.getRegistrationNumber());
        existingBus.setCapacity(bus.getCapacity());
        existingBus.setType(bus.getType());

        return busRepo.save(existingBus);
    }

    @Override
    public void deleteBus(Integer busId) {

        Bus bus = getBusById(busId);

        busRepo.delete(bus);
    }
}