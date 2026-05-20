package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDTO;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDTO;

import com.busticket.busticketbooking.entity.Route;

public class RouteMapper {

    private RouteMapper() {
    }

    public static Route mapToEntity(
            RouteRequestDTO dto) {

        Route route = new Route();

        route.setFromCity(dto.getFromCity());

        route.setToCity(dto.getToCity());

        route.setBreakPoints(dto.getBreakPoints());

        route.setDuration(dto.getDuration());

        return route;
    }

    public static RouteResponseDTO mapToResponseDTO(
            Route route) {

        RouteResponseDTO dto =
                new RouteResponseDTO();

        dto.setId(route.getId());

        dto.setFromCity(route.getFromCity());

        dto.setToCity(route.getToCity());

        dto.setBreakPoints(route.getBreakPoints());

        dto.setDuration(route.getDuration());

        return dto;
    }

    public static void updateEntityFromDTO(
            RouteRequestDTO dto,
            Route route) {

        route.setFromCity(dto.getFromCity());

        route.setToCity(dto.getToCity());

        route.setBreakPoints(dto.getBreakPoints());

        route.setDuration(dto.getDuration());
    }
}