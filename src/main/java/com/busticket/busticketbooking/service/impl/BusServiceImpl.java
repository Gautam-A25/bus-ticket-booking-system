package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.service.BusService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.mapper.BusMapper;

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
    public BusResponseDTO createBus(BusRequestDTO dto) {

        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Office not found"));

        Bus bus = new Bus();

        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());

        Bus savedBus = busRepo.save(bus);

        return BusMapper.mapToResponseDto(savedBus);
    }

    @Override
    public List<BusResponseDTO> getAllBuses() {

        return busRepo.findAll()
                .stream()
                .map(BusMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusResponseDTO getBusById(Integer id) {

        Bus bus = busRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bus with ID " + id + " not found"));

        return BusMapper.mapToResponseDto(bus);
    }

    @Override
    public List<BusResponseDTO> getBusesByOffice(Integer officeId) {

        List<Bus> buses = busRepo.findAll()
                .stream()
                .filter(bus ->
                        bus.getOffice() != null &&
                        bus.getOffice().getId().equals(officeId))
                .collect(Collectors.toList());

        return buses.stream()
                .map(BusMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusResponseDTO updateBus(Integer busId,
                                    BusRequestDTO dto) {

        Bus bus = busRepo.findById(busId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bus with ID " + busId + " not found"));

        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Office not found"));

        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());

        Bus updatedBus = busRepo.save(bus);

        return BusMapper.mapToResponseDto(updatedBus);
    }

    @Override
    public String deleteBus(Integer id) {

        Bus bus = busRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bus with ID " + id + " not found"
                        ));

        String busDetails =
                "Bus Deleted Successfully : \n" +
                        "ID = " + bus.getId() + "\n" +
                        "Office ID = " +
                        (bus.getOffice() != null
                                ? bus.getOffice().getId()
                                : null) + "\n" +
                        "Registration Number = " + bus.getRegistrationNumber() + "\n" +
                        "Capacity = " + bus.getCapacity() + "\n" +
                        "Type = " + bus.getType();

        busRepo.delete(bus);

        return busDetails;
    }
}