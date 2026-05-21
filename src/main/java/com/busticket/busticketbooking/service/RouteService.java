package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDTO;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDTO;

import java.util.List;

public interface RouteService {

    List<RouteResponseDTO> getAllRoutes();

    RouteResponseDTO getRouteById(Integer id);

    RouteResponseDTO addRoute(RouteRequestDTO routeRequestDto);

    RouteResponseDTO updateRoute(Integer id,
                                 RouteRequestDTO routeRequestDto);

    String deleteRoute(Integer id);
}