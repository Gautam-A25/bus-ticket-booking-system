package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDTO;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDTO;

import java.util.List;

/**
 * Service interface defining all route management operations.
 *
 * <p>A {@code Route} defines the city-to-city path (fromCity → toCity) along
 * with optional breakpoints and estimated duration. Routes are linked to
 * {@code Trip}s. Deleting a route cascades through all associated trips,
 * bookings, payments, and reviews.</p>
 */
public interface RouteService {

    /** Returns all routes in the system (no pagination). */
    List<RouteResponseDTO> getAllRoutes();

    /** Returns the route with the given ID; throws 404 if not found. */
    RouteResponseDTO getRouteById(Integer id);

    /** Creates and persists a new route from the given request data. */
    RouteResponseDTO addRoute(RouteRequestDTO routeRequestDto);

    /** Updates fromCity, toCity, breakpoints, and duration of an existing route; throws 404 if not found. */
    RouteResponseDTO updateRoute(Integer id,
                                 RouteRequestDTO routeRequestDto);

    /**
     * Deletes a route and cascades through all dependent trips, bookings,
     * payments, and reviews.
     *
     * @return a formatted summary string of the deleted record
     */
    String deleteRoute(Integer id);
}