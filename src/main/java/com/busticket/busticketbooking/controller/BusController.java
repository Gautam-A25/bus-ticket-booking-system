package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.service.BusService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Bus> registerBus(@PathVariable Integer officeId,
                           @RequestBody Bus bus) {

        return ResponseEntity.ok(busService.registerBus(officeId, bus));
    }

    @GetMapping("/offices/{officeId}/buses")
    public ResponseEntity<List<Bus>> getBusesByOffice(@PathVariable Integer officeId) {

        return ResponseEntity.ok(busService.getBusesByOffice(officeId));
    }

    @GetMapping("/buses/{busId}")
    public ResponseEntity<Bus> getBusById(@PathVariable Integer busId) {

        return ResponseEntity.ok(busService.getBusById(busId));
    }

    @PutMapping("/buses/{busId}")
    public ResponseEntity<Bus> updateBus(@PathVariable Integer busId,
                         @RequestBody Bus bus) {

        return ResponseEntity.ok(busService.updateBus(busId, bus));
    }

    @DeleteMapping("/buses/{busId}")
    public String deleteBus(@PathVariable Integer busId) {

        busService.deleteBus(busId);

        return "Bus Deleted Successfully";
    }
}