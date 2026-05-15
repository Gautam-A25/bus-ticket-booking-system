package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.service.BusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/offices/{officeId}/buses")
    public Bus registerBus(@PathVariable Integer officeId,
                           @RequestBody Bus bus) {

        return busService.registerBus(officeId, bus);
    }

    @GetMapping("/offices/{officeId}/buses")
    public List<Bus> getBusesByOffice(@PathVariable Integer officeId) {

        return busService.getBusesByOffice(officeId);
    }

    @GetMapping("/buses/{busId}")
    public Bus getBusById(@PathVariable Integer busId) {

        return busService.getBusById(busId);
    }

    @PutMapping("/buses/{busId}")
    public Bus updateBus(@PathVariable Integer busId,
                         @RequestBody Bus bus) {

        return busService.updateBus(busId, bus);
    }

    @DeleteMapping("/buses/{busId}")
    public String deleteBus(@PathVariable Integer busId) {

        busService.deleteBus(busId);

        return "Bus Deleted Successfully";
    }
}