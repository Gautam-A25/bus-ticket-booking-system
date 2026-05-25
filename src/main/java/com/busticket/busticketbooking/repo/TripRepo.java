package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepo
        extends JpaRepository<Trip, Integer> {

    /*
     * Search trips using
     * source and destination cities
     */
    List<Trip> findByRoute_FromCityAndRoute_ToCity(

            String fromCity,

            String toCity
    );

    /*
     * Search trips using
     * source city,
     * destination city,
     * and trip date
     */
    List<Trip>
    findByRoute_FromCityAndRoute_ToCityAndTripDate(

            String fromCity,

            String toCity,

            LocalDateTime tripDate
    );

    List<Trip> findByBusId(Integer busId);

    List<Trip> findByRouteId(Integer routeId);

    List<Trip> findByDriver1IdOrDriver2Id(Integer driver1Id, Integer driver2Id);

    List<Trip> findByBoardingAddressIdOrDroppingAddressId(Integer boardingAddressId, Integer droppingAddressId);
}