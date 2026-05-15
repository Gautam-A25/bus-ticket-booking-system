package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.service.DriverService;
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
    public Driver registerDriver(@PathVariable Integer officeId,
                                 @RequestBody Driver driver) {

        return driverService.registerDriver(officeId, driver);
    }

    @GetMapping("/offices/{officeId}/drivers")
    public List<Driver> getDriversByOffice(@PathVariable Integer officeId) {

        return driverService.getDriversByOffice(officeId);
    }

    @GetMapping("/drivers/{driverId}")
    public Driver getDriverById(@PathVariable Integer driverId) {

        return driverService.getDriverById(driverId);
    }

    @PutMapping("/drivers/{driverId}")
    public Driver updateDriver(@PathVariable Integer driverId,
                               @RequestBody Driver driver) {

        return driverService.updateDriver(driverId, driver);
    }

    @DeleteMapping("/drivers/{driverId}")
    public String deleteDriver(@PathVariable Integer driverId) {

        driverService.deleteDriver(driverId);

        return "Driver Deleted Successfully";
    }
}