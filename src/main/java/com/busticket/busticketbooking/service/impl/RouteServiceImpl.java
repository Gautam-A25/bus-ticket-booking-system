package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.RouteDto;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.repo.RouteRepo;
import com.busticket.busticketbooking.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepo routeRepo;

    @Override
    public List<RouteDto> getAllRoutes() {

        return routeRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public RouteDto getRouteById(Integer id) {

        Route route = routeRepo.findById(id).orElse(null);

        return mapToDto(route);
    }

    @Override
    public RouteDto addRoute(RouteDto dto) {

        Route savedRoute = routeRepo.save(mapToEntity(dto));

        return mapToDto(savedRoute);
    }

    private RouteDto mapToDto(Route route) {

        RouteDto dto = new RouteDto();

        dto.setId(route.getId());
        dto.setFromCity(route.getFromCity());
        dto.setToCity(route.getToCity());
        dto.setBreakPoints(route.getBreakPoints());
        dto.setDuration(route.getDuration());

        return dto;
    }

    private Route mapToEntity(RouteDto dto) {

        Route route = new Route();

        route.setId(dto.getId());
        route.setFromCity(dto.getFromCity());
        route.setToCity(dto.getToCity());
        route.setBreakPoints(dto.getBreakPoints());
        route.setDuration(dto.getDuration());

        return route;
    }
}