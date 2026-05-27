package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for {@link Trip} database operations.
 *
 * <p>Handles searching for bookable trips by route cities, trip date, assigned bus,
 * assigned drivers, addresses, and route IDs.</p>
 */
@Repository
public interface TripRepo
        extends JpaRepository<Trip, Integer> {

    /**
     * Searches for trips using origin and destination cities.
     *
     * @param fromCity origin city
     * @param toCity   destination city
     * @return a list of matching trips
     */
    List<Trip> findByRoute_FromCityAndRoute_ToCity(
            String fromCity,
            String toCity
    );

    /**
     * Searches for trips using origin city, destination city, and a specific trip date.
     *
     * @param fromCity origin city
     * @param toCity   destination city
     * @param tripDate trip departure date-time
     * @return a list of matching trips
     */
    List<Trip> findByRoute_FromCityAndRoute_ToCityAndTripDate(
            String fromCity,
            String toCity,
            LocalDateTime tripDate
    );

    /**
     * Finds all trips assigned to a specific bus.
     *
     * @param busId ID of the bus
     * @return a list of trips using this bus
     */
    List<Trip> findByBusId(Integer busId);

    /**
     * Finds all trips associated with a specific route ID.
     *
     * @param routeId ID of the route
     * @return a list of trips operating on this route
     */
    List<Trip> findByRouteId(Integer routeId);

    /**
     * Finds all trips where the given driver is assigned either as primary (driver1) or backup (driver2).
     *
     * @param driver1Id ID of driver 1
     * @param driver2Id ID of driver 2
     * @return a list of trips where the driver is assigned
     */
    List<Trip> findByDriver1IdOrDriver2Id(Integer driver1Id, Integer driver2Id);

    /**
     * Finds all trips where the given address is used either as boarding or dropping address.
     *
     * @param boardingAddressId ID of the boarding address
     * @param droppingAddressId ID of the dropping address
     * @return a list of trips using the address
     */
    List<Trip> findByBoardingAddressIdOrDroppingAddressId(Integer boardingAddressId, Integer droppingAddressId);
}