package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDTO;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDTO;

import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.entity.Trip;
/*
 * Mapper class used for converting
 * Trip Entity ↔ Trip DTO.
 */
public class TripMapper {
        /*
     * Private constructor to prevent
     * object creation.
     */
    private TripMapper() {
    }
     /*
     * Converts TripRequestDTO
     * into Trip Entity.
     */
    public static Trip mapToEntity(
            TripRequestDTO dto) {

        Trip trip = new Trip();

        Route route = new Route();
        route.setId(dto.getRouteId());
        trip.setRoute(route);

        Bus bus = new Bus();
        bus.setId(dto.getBusId());
        trip.setBus(bus);

        Address boardingAddress = new Address();
        boardingAddress.setId(
                dto.getBoardingAddressId());
        trip.setBoardingAddress(boardingAddress);

        Address droppingAddress = new Address();
        droppingAddress.setId(
                dto.getDroppingAddressId());
        trip.setDroppingAddress(droppingAddress);

        Driver driver1 = new Driver();
        driver1.setId(dto.getDriver1Id());
        trip.setDriver1(driver1);

        Driver driver2 = new Driver();
        driver2.setId(dto.getDriver2Id());
        trip.setDriver2(driver2);

        trip.setDepartureTime(
                dto.getDepartureTime());

        trip.setArrivalTime(
                dto.getArrivalTime());

        trip.setAvailableSeats(
                dto.getAvailableSeats());

        trip.setFare(dto.getFare());

        trip.setTripDate(dto.getTripDate());

        return trip;
    }

    public static TripResponseDTO mapToResponseDTO(
            Trip trip) {

        TripResponseDTO dto =
                new TripResponseDTO();

        dto.setId(trip.getId());

        dto.setRouteId(
                trip.getRoute().getId());

        dto.setBusId(
                trip.getBus().getId());

        dto.setBoardingAddressId(
                trip.getBoardingAddress().getId());

        dto.setDroppingAddressId(
                trip.getDroppingAddress().getId());

        dto.setDepartureTime(
                trip.getDepartureTime());

        dto.setArrivalTime(
                trip.getArrivalTime());

        dto.setDriver1Id(
                trip.getDriver1().getId());

        dto.setDriver2Id(
                trip.getDriver2().getId());

        dto.setAvailableSeats(
                trip.getAvailableSeats());

        dto.setFare(trip.getFare());

        dto.setTripDate(
                trip.getTripDate());

        return dto;
    }

    public static void updateEntityFromDTO(
            TripRequestDTO dto,
            Trip trip) {

        Route route = new Route();
        route.setId(dto.getRouteId());
        trip.setRoute(route);

        Bus bus = new Bus();
        bus.setId(dto.getBusId());
        trip.setBus(bus);

        Address boardingAddress = new Address();
        boardingAddress.setId(
                dto.getBoardingAddressId());
        trip.setBoardingAddress(boardingAddress);

        Address droppingAddress = new Address();
        droppingAddress.setId(
                dto.getDroppingAddressId());
        trip.setDroppingAddress(droppingAddress);

        Driver driver1 = new Driver();
        driver1.setId(dto.getDriver1Id());
        trip.setDriver1(driver1);

        Driver driver2 = new Driver();
        driver2.setId(dto.getDriver2Id());
        trip.setDriver2(driver2);

        trip.setDepartureTime(
                dto.getDepartureTime());

        trip.setArrivalTime(
                dto.getArrivalTime());

        trip.setAvailableSeats(
                dto.getAvailableSeats());

        trip.setFare(dto.getFare());

        trip.setTripDate(dto.getTripDate());
    }
}