package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDto;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDto;

import java.util.List;

public interface RouteService {

    List<RouteResponseDto> getAllRoutes();

    RouteResponseDto getRouteById(Integer id);

    RouteResponseDto addRoute(RouteRequestDto routeRequestDto);

    RouteResponseDto updateRoute(Integer id,
                                 RouteRequestDto routeRequestDto);

    void deleteRoute(Integer id);
}