package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.RouteDTO.RouteRequestDTO;
import com.busticket.busticketbooking.dto.RouteDTO.RouteResponseDTO;

import com.busticket.busticketbooking.entity.Route;
/*
 * Mapper class used for converting
 * Route Entity ↔ Route DTO.
 */
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
     /*
     * Converts Route Entity
     * into RouteResponseDTO.
     */
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
      /*
     * Updates existing Route Entity
     * using RouteRequestDTO data.
     */
    public static void updateEntityFromDTO(
            RouteRequestDTO dto,
            Route route) {

        route.setFromCity(dto.getFromCity());

        route.setToCity(dto.getToCity());

        route.setBreakPoints(dto.getBreakPoints());

        route.setDuration(dto.getDuration());
    }
}