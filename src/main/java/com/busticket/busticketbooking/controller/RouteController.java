package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDTO;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDTO;
import com.busticket.busticketbooking.service.RouteService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<RouteResponseDTO> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public RouteResponseDTO getRouteById(@PathVariable Integer id) {
        return routeService.getRouteById(id);
    }

    @PostMapping
    public RouteResponseDTO addRoute(
            @Valid @RequestBody RouteRequestDTO routeRequestDto) {

        return routeService.addRoute(routeRequestDto);
    }

    @PutMapping("/{id}")
    public RouteResponseDTO updateRoute(
            @PathVariable Integer id,
            @Valid @RequestBody RouteRequestDTO routeRequestDto) {

        return routeService.updateRoute(id, routeRequestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteRoute(@PathVariable Integer id) {

        return routeService.deleteRoute(id);
    }
}