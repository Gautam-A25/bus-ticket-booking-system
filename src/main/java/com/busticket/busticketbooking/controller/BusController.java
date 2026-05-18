package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BusDto;
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
    public BusDto registerBus(@PathVariable Integer officeId,
                              @RequestBody BusDto busDto) {

        return busService.registerBus(officeId, busDto);
    }

    @GetMapping("/offices/{officeId}/buses")
    public List<BusDto> getBusesByOffice(@PathVariable Integer officeId) {

        return ResponseEntity.ok(busService.getBusesByOffice(officeId));
    }

    @GetMapping("/buses/{busId}")
    public BusDto getBusById(@PathVariable Integer busId) {

        return ResponseEntity.ok(busService.getBusById(busId));
    }

    @PutMapping("/buses/{busId}")
    public BusDto updateBus(@PathVariable Integer busId,
                            @RequestBody BusDto busDto) {

        return busService.updateBus(busId, busDto);
    }

    @DeleteMapping("/buses/{busId}")
    public String deleteBus(@PathVariable Integer busId) {

        busService.deleteBus(busId);

        return "Bus Deleted Successfully";
    }
}