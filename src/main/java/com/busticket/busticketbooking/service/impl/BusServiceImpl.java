package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.BusDto;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.service.BusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepo busRepo;

    public BusServiceImpl(BusRepo busRepo) {
        this.busRepo = busRepo;
    }

    @Override
    public BusDto registerBus(Integer officeId, BusDto busDto) {

        Bus bus = new Bus();

        bus.setRegistrationNumber(busDto.getRegistrationNumber());
        bus.setCapacity(busDto.getCapacity());
        bus.setType(busDto.getType());

        Bus savedBus = busRepo.save(bus);

        return mapToDto(savedBus);
    }

    @Override
    public List<BusDto> getBusesByOffice(Integer officeId) {

        return busRepo.findByOffice_id(officeId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusDto getBusById(Integer busId) {

        Bus bus = busRepo.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus Not Found"));

        return mapToDto(bus);
    }

    @Override
    public BusDto updateBus(Integer busId, BusDto busDto) {

        Bus existingBus = busRepo.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus Not Found"));

        existingBus.setRegistrationNumber(busDto.getRegistrationNumber());
        existingBus.setCapacity(busDto.getCapacity());
        existingBus.setType(busDto.getType());

        Bus updatedBus = busRepo.save(existingBus);

        return mapToDto(updatedBus);
    }

    @Override
    public void deleteBus(Integer busId) {

        busRepo.deleteById(busId);
    }

    private BusDto mapToDto(Bus bus) {

        BusDto dto = new BusDto();

        dto.setBusId(bus.getId());
        dto.setRegistrationNumber(bus.getRegistrationNumber());
        dto.setCapacity(bus.getCapacity());
        dto.setType(bus.getType());

        return dto;
    }
}