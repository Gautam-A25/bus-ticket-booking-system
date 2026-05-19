package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDto;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDto;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.repo.RouteRepo;
import com.busticket.busticketbooking.service.RouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepo routeRepo;

    @Override
    public List<RouteResponseDto> getAllRoutes() {

        List<Route> routes = routeRepo.findAll();

        return routes.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public RouteResponseDto getRouteById(Integer id) {

        Route route = routeRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Route not found with id: " + id));

        return mapToResponseDto(route);
    }

    @Override
    public RouteResponseDto addRoute(RouteRequestDto routeRequestDto) {

        Route route = new Route();

        route.setFromCity(routeRequestDto.getFromCity());
        route.setToCity(routeRequestDto.getToCity());
        route.setBreakPoints(routeRequestDto.getBreakPoints());
        route.setDuration(routeRequestDto.getDuration());

        Route savedRoute = routeRepo.save(route);

        return mapToResponseDto(savedRoute);
    }

    @Override
    public RouteResponseDto updateRoute(Integer id,
                                        RouteRequestDto routeRequestDto) {

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
    public void deleteRoute(Integer id) {

        Route route = routeRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Route not found with id: " + id));

        routeRepo.delete(route);
    }

    private RouteResponseDto mapToResponseDto(Route route) {

        RouteResponseDto dto = new RouteResponseDto();

        dto.setId(route.getId());
        dto.setFromCity(route.getFromCity());
        dto.setToCity(route.getToCity());
        dto.setBreakPoints(route.getBreakPoints());
        dto.setDuration(route.getDuration());

        return dto;
    }
}