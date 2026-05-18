package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.DriverDto;
import com.busticket.busticketbooking.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/offices/{officeId}/drivers")
    public DriverDto registerDriver(@PathVariable Integer officeId,
                                    @RequestBody DriverDto driverDto) {

        return driverService.registerDriver(officeId, driverDto);
    }

    @GetMapping("/offices/{officeId}/drivers")
    public List<DriverDto> getDriversByOffice(@PathVariable Integer officeId) {

        return ResponseEntity.ok(driverService.getDriversByOffice(officeId));
    }

    @GetMapping("/drivers/{driverId}")
    public DriverDto getDriverById(@PathVariable Integer driverId) {

        return ResponseEntity.ok(driverService.getDriverById(driverId));
    }

    @PutMapping("/drivers/{driverId}")
    public DriverDto updateDriver(@PathVariable Integer driverId,
                                  @RequestBody DriverDto driverDto) {

        return driverService.updateDriver(driverId, driverDto);
    }

    @DeleteMapping("/drivers/{driverId}")
    public String deleteDriver(@PathVariable Integer driverId) {

        driverService.deleteDriver(driverId);

        return "Driver Deleted Successfully";
    }
}