package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.BusDto.BusRequestDto;
import com.busticket.busticketbooking.dto.BusDto.BusResponseDto;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.service.BusService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepo busRepo;
    private final AgencyOfficeRepo officeRepo;

    public BusServiceImpl(BusRepo busRepo,
                          AgencyOfficeRepo officeRepo) {

        this.busRepo = busRepo;
        this.officeRepo = officeRepo;
    }

    @Override
    public BusResponseDto createBus(BusRequestDto dto) {

        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new RuntimeException("Office not found"));

        Bus bus = new Bus();

        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());

        Bus savedBus = busRepo.save(bus);

        return mapToResponseDto(savedBus);
    }

    @Override
    public List<BusResponseDto> getAllBuses() {

        return busRepo.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusResponseDto getBusById(Integer id) {

        Bus bus = busRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        return mapToResponseDto(bus);
    }

    @Override
    public List<BusResponseDto> getBusesByOffice(Integer officeId) {

        List<Bus> buses = busRepo.findAll()
                .stream()
                .filter(bus ->
                        bus.getOffice() != null &&
                        bus.getOffice().getId().equals(officeId))
                .collect(Collectors.toList());

        return buses.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusResponseDto updateBus(Integer busId,
                                    BusRequestDto dto) {

        Bus bus = busRepo.findById(busId)
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new RuntimeException("Office not found"));

        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());

        Bus updatedBus = busRepo.save(bus);

        return mapToResponseDto(updatedBus);
    }

    @Override
    public void deleteBus(Integer id) {

        Bus bus = busRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        busRepo.delete(bus);
    }

    private BusResponseDto mapToResponseDto(Bus bus) {

        BusResponseDto dto = new BusResponseDto();

        dto.setId(bus.getId());

        if (bus.getOffice() != null) {
            dto.setOfficeId(bus.getOffice().getId());
        }

        dto.setRegistrationNumber(bus.getRegistrationNumber());
        dto.setCapacity(bus.getCapacity());
        dto.setType(bus.getType());

        return dto;
    }
}