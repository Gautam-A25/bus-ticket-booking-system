package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/routes")
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/routes/{id}")
    public Route getRouteById(@PathVariable Integer id) {
        return routeService.getRouteById(id);
    }

    @PostMapping("/routes")
    public Route addRoute(@RequestBody Route route) {
        return routeService.addRoute(route);
    }

    @GetMapping("/routes/search")
    public List<Route> searchRoutes(
            @RequestParam String fromCity,
            @RequestParam String toCity) {

        return routeService.searchRoutes(
                fromCity,
                toCity
        );
    }
}