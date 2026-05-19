package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.RouteDto.RouteRequestDto;
import com.busticket.busticketbooking.dto.RouteDto.RouteResponseDto;

import java.util.List;

public interface RouteService {

    List<RouteResponseDto> getAllRoutes();

    RouteResponseDto getRouteById(Integer id);

    RouteResponseDto addRoute(RouteRequestDto routeRequestDto);

    RouteResponseDto updateRoute(Integer id,
                                 RouteRequestDto routeRequestDto);

    void deleteRoute(Integer id);
}