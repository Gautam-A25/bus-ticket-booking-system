package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.DriverDto.DriverRequestDto;
import com.busticket.busticketbooking.dto.DriverDto.DriverResponseDto;
import com.busticket.busticketbooking.service.DriverService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public DriverResponseDto createDriver(
            @Valid @RequestBody DriverRequestDto dto) {

        return driverService.createDriver(dto);
    }

    @GetMapping
    public List<DriverResponseDto> getAllDrivers() {

        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public DriverResponseDto getDriverById(@PathVariable Integer id) {

        return driverService.getDriverById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Integer id) {

        driverService.deleteDriver(id);
    }
}