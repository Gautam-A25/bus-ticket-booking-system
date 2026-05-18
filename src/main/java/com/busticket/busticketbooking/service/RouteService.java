package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.RouteDto;

import java.util.List;

public interface RouteService {

    List<RouteDto> getAllRoutes();

    RouteDto getRouteById(Integer id);

    RouteDto addRoute(RouteDto dto);
}