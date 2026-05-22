package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
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
    public DriverResponseDTO createDriver(
            @Valid @RequestBody DriverRequestDTO dto) {

        return driverService.createDriver(dto);
    }

    @GetMapping
    public List<DriverResponseDTO> getAllDrivers() {

        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public DriverResponseDTO getDriverById(@PathVariable Integer id) {

        return driverService.getDriverById(id);
    }

    @GetMapping("/office/{officeId}")
    public List<DriverResponseDTO> getDriversByOffice(@PathVariable Integer officeId) {

        return driverService.getDriversByOffice(officeId);
    }

    @PutMapping("/{id}")
    public DriverResponseDTO updateDriver(
            @PathVariable Integer id,
            @Valid @RequestBody DriverRequestDTO dto) {

        return driverService.updateDriver(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Integer id) {

        driverService.deleteDriver(id);
    }
}