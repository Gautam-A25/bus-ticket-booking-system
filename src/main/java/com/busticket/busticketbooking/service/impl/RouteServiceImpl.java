package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDTO;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDTO;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.RouteRepo;
import com.busticket.busticketbooking.service.RouteService;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Review;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of {@link RouteService}.
 *
 * <p>Routes define the city-to-city path and are linked to {@code Trip}s.
 * This class uses {@code @Autowired} field injection for all repositories
 * because it was originally written without constructor injection.</p>
 *
 * <p><b>Note:</b> {@code getRouteById} and {@code updateRoute} throw
 * {@link RuntimeException} instead of the custom {@link ResourceNotFoundException};
 * these could be unified in a future refactor.</p>
 *
 * <p><b>Cascade-delete in {@code deleteRoute}:</b> Trips linked to the route are
 * deleted first (with their bookings, payments, and reviews), then the route itself.</p>
 */
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepo routeRepo;

    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    /** Returns all routes in the system as a list of response DTOs. */
    @Override
    public List<RouteResponseDTO> getAllRoutes() {
        List<Route> routes = routeRepo.findAll();
        return routes.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /** Fetches a route by ID; throws {@link RuntimeException} if not found. */
    @Override
    public RouteResponseDTO getRouteById(Integer id) {
        Route route = routeRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Route not found with id: " + id));
        return mapToResponseDto(route);
    }

    /** Creates and persists a new route from the given DTO. */
    @Override
    public RouteResponseDTO addRoute(RouteRequestDTO routeRequestDto) {
        Route route = new Route();
        route.setFromCity(routeRequestDto.getFromCity());
        route.setToCity(routeRequestDto.getToCity());
        route.setBreakPoints(routeRequestDto.getBreakPoints());
        route.setDuration(routeRequestDto.getDuration());
        Route savedRoute = routeRepo.save(route);
        return mapToResponseDto(savedRoute);
    }

    /** Updates all fields of an existing route; throws {@link RuntimeException} if not found. */
    @Override
    public RouteResponseDTO updateRoute(Integer id,
                                        RouteRequestDTO routeRequestDto) {
        Route route = routeRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Route not found with id: " + id));

        route.setFromCity(routeRequestDto.getFromCity());
        route.setToCity(routeRequestDto.getToCity());
        route.setBreakPoints(routeRequestDto.getBreakPoints());
        route.setDuration(routeRequestDto.getDuration());

        Route updatedRoute = routeRepo.save(route);
        return mapToResponseDto(updatedRoute);
    }

    @Override
    @Transactional
    public String deleteRoute(Integer id) {

        Route route = routeRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Route with ID " + id + " not found"
                        ));

        String routeDetails =
                "Route Deleted Successfully : \n" +
                        "ID = " + route.getId() + "\n" +
                        "From City = " + route.getFromCity() + "\n" +
                        "To City = " + route.getToCity() + "\n" +
                        "Break Points = " + route.getBreakPoints() + "\n" +
                        "Duration = " + route.getDuration();

        // Find and delete trips referencing this route
        List<Trip> trips = tripRepo.findByRouteId(id);
        for (Trip trip : trips) {
            // Cascade delete bookings and payments first
            List<Booking> bookings = bookingRepo.findByTripId(trip.getId());
            for (Booking booking : bookings) {
                paymentRepo.findByBookingId(booking.getId()).ifPresent(paymentRepo::delete);
            }
            bookingRepo.deleteAll(bookings);

            // Cascade delete reviews
            List<Review> reviews = reviewRepo.findByTripId(trip.getId());
            reviewRepo.deleteAll(reviews);

            // Delete trip
            tripRepo.delete(trip);
        }

        routeRepo.delete(route);

        return routeDetails;
    }

    /**
     * Private helper that maps a {@link Route} entity to its response DTO.
     * Used internally by all read operations.
     */
    private RouteResponseDTO mapToResponseDto(Route route) {
        RouteResponseDTO dto = new RouteResponseDTO();
        dto.setId(route.getId());
        dto.setFromCity(route.getFromCity());
        dto.setToCity(route.getToCity());
        dto.setBreakPoints(route.getBreakPoints());
        dto.setDuration(route.getDuration());
        return dto;
    }
}