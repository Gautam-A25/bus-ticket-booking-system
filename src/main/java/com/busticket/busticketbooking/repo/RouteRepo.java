package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Route} database operations.
 *
 * <p>Handles route queries, such as searching by origin and destination cities.</p>
 */
@Repository
public interface RouteRepo extends JpaRepository<Route, Integer> {
    /**
     * Finds all routes with the matching source and destination cities.
     *
     * @param fromCity origin city
     * @param toCity   destination city
     * @return a list of routes connecting these cities
     */
    List<Route> findByFromCityAndToCity(String fromCity, String toCity);
}