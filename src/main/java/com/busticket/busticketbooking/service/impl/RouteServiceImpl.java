package com.busticket.busticketbooking.service.impl;

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
    public List<Route> getAllRoutes() {
        return routeRepo.findAll();
    }

    @Override
    public Route getRouteById(Integer id) {
        return routeRepo.findById(id).orElse(null);
    }

    @Override
    public Route addRoute(Route route) {
        return routeRepo.save(route);
    }

    @Override
    public List<Route> searchRoutes(String fromCity,
                                    String toCity) {

        return routeRepo.findByFromCityAndToCity(
                fromCity,
                toCity
        );
    }
}