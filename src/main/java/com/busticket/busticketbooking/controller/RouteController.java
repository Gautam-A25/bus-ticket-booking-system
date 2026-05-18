package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.RouteDto;
import com.busticket.busticketbooking.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<RouteDto> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public RouteDto getRouteById(@PathVariable Integer id) {
        return routeService.getRouteById(id);
    }

    @PostMapping
    public RouteDto addRoute(@RequestBody RouteDto dto) {
        return routeService.addRoute(dto);
    }
}