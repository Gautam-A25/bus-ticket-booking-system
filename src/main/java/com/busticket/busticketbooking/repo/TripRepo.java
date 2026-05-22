package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepo extends JpaRepository<Trip, Integer> {
    // Finds trips using source and destination cities from route
    List<Trip> findByRoute_FromCityAndRoute_ToCity(
            String fromCity,
            String toCity
    );
}