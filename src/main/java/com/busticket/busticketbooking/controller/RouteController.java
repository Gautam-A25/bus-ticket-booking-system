package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/routes")
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/routes/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Integer id) {
        return ResponseEntity.ok(routeService.getRouteById(id));
    }

    @PostMapping("/routes")
    public ResponseEntity<Route> addRoute(@RequestBody Route route) {
        return ResponseEntity.ok(routeService.addRoute(route));
    }

    @GetMapping("/routes/search")
    public ResponseEntity<List<Route>> searchRoutes(
            @RequestParam String fromCity,
            @RequestParam String toCity) {

        return ResponseEntity.ok(routeService.searchRoutes(fromCity, toCity));
    }
}