package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDto;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDto;
import com.busticket.busticketbooking.service.BusService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping
    public BusResponseDto createBus(
            @Valid @RequestBody BusRequestDto dto) {

        return busService.createBus(dto);
    }

    @GetMapping
    public List<BusResponseDto> getAllBuses() {

        return busService.getAllBuses();
    }

    @GetMapping("/{id}")
    public BusResponseDto getBusById(
            @PathVariable Integer id) {

        return busService.getBusById(id);
    }

    @GetMapping("/office/{officeId}")
    public List<BusResponseDto> getBusesByOffice(
            @PathVariable Integer officeId) {

        return busService.getBusesByOffice(officeId);
    }

    @PutMapping("/{id}")
    public BusResponseDto updateBus(
            @PathVariable Integer id,
            @Valid @RequestBody BusRequestDto dto) {

        return busService.updateBus(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBus(
            @PathVariable Integer id) {

        busService.deleteBus(id);
    }
}