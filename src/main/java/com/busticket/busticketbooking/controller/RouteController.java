package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDto;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDto;
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
    public List<RouteResponseDto> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public RouteResponseDto getRouteById(@PathVariable Integer id) {
        return routeService.getRouteById(id);
    }

    @PostMapping
    public RouteResponseDto addRoute(
            @Valid @RequestBody RouteRequestDto routeRequestDto) {

        return routeService.addRoute(routeRequestDto);
    }

    @PutMapping("/{id}")
    public RouteResponseDto updateRoute(
            @PathVariable Integer id,
            @Valid @RequestBody RouteRequestDto routeRequestDto) {

        return routeService.updateRoute(id, routeRequestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteRoute(@PathVariable Integer id) {

        routeService.deleteRoute(id);

        return "Route deleted successfully";
    }
}