package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepo extends JpaRepository<Route, Integer> {
     // Finds routes based on source and destination cities
    List<Route> findByFromCityAndToCity(String fromCity, String toCity);
}