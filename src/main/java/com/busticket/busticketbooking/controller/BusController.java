package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
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
    public BusResponseDTO createBus(
            @Valid @RequestBody BusRequestDTO dto) {

        return busService.createBus(dto);
    }

    @GetMapping
    public List<BusResponseDTO> getAllBuses() {

        return busService.getAllBuses();
    }

    @GetMapping("/{id}")
    public BusResponseDTO getBusById(
            @PathVariable Integer id) {

        return busService.getBusById(id);
    }

    @GetMapping("/office/{officeId}")
    public List<BusResponseDTO> getBusesByOffice(
            @PathVariable Integer officeId) {

        return busService.getBusesByOffice(officeId);
    }

    @PutMapping("/{id}")
    public BusResponseDTO updateBus(
            @PathVariable Integer id,
            @Valid @RequestBody BusRequestDTO dto) {

        return busService.updateBus(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBus(
            @PathVariable Integer id) {

        busService.deleteBus(id);
    }
}