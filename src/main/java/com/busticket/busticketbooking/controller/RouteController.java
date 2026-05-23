package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDTO;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDTO;
import com.busticket.busticketbooking.service.RouteService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
 * RestController is used to create REST APIs.
 * All methods inside this class will return JSON responses.
 */
@RestController
/*
 * Base URL for all Route APIs.
 * Example:
 * http://localhost:8080/api/v1/routes
 */
@RequestMapping("/api/v1/routes")
public class RouteController {
    /*
     * Injecting RouteService dependency.
     * Service layer contains business logic.
     */
    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<RouteResponseDTO> getAllRoutes() {
        return routeService.getAllRoutes();
    }
     /*
     * GET API to fetch route by ID.
     * GET /api/v1/routes/{id}
     */
    @GetMapping("/{id}")
    public RouteResponseDTO getRouteById(@PathVariable Integer id) {
        return routeService.getRouteById(id);
    }
     /*
     * POST API to create a new route.
     *
     * URL:
     * POST /api/v1/routes
     *
     * @RequestBody converts JSON request into Java object.
     *
     * @Valid performs validation on DTO fields.
     */
    @PostMapping
    public RouteResponseDTO addRoute(
            @Valid @RequestBody RouteRequestDTO routeRequestDto) {

        return routeService.addRoute(routeRequestDto);
    }
    /*
     * PUT API to update existing route.
     *
     * URL:
     * PUT /api/v1/routes/{id}
     *
     * Example:
     * PUT /api/v1/routes/1
     *
     * @PathVariable -> Route ID
     * @RequestBody -> Updated route details
     */
    @PutMapping("/{id}")
    public RouteResponseDTO updateRoute(
            @PathVariable Integer id,
            @Valid @RequestBody RouteRequestDTO routeRequestDto) {

        return routeService.updateRoute(id, routeRequestDto);
    }
    /*
     * DELETE API to remove route by ID.
     *
     * URL:
     * DELETE /api/v1/routes/{id}
     *
     * Example:
     * DELETE /api/v1/routes/1
     */
    @DeleteMapping("/{id}")
    public String deleteRoute(@PathVariable Integer id) {

        return routeService.deleteRoute(id);
    }
}