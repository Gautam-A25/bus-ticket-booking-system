package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.entity.Route;

import java.util.List;

public interface RouteService {

    List<Route> getAllRoutes();

    Route getRouteById(Integer id);

    Route addRoute(Route route);

    List<Route> searchRoutes(String fromCity,
                             String toCity);
}