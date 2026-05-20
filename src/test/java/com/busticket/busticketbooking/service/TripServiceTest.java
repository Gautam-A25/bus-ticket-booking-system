package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDTO;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDTO;

import com.busticket.busticketbooking.entity.*;

import com.busticket.busticketbooking.repo.TripRepo;

import com.busticket.busticketbooking.service.impl.TripServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class TripServiceTest {

    @Mock
    private TripRepo tripRepo;

    @InjectMocks
    private TripServiceImpl tripService;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTripsTest() {

        Route route = new Route();
        route.setId(1);

        Bus bus = new Bus();
        bus.setId(1);

        Address address = new Address();
        address.setId(1);

        Driver driver = new Driver();
        driver.setId(1);

        Trip trip = new Trip();

        trip.setId(1);

        trip.setRoute(route);
        trip.setBus(bus);

        trip.setBoardingAddress(address);
        trip.setDroppingAddress(address);

        trip.setDriver1(driver);
        trip.setDriver2(driver);

        trip.setAvailableSeats(35);

        trip.setFare(BigDecimal.valueOf(999));

        trip.setDepartureTime(LocalDateTime.now());
        trip.setArrivalTime(LocalDateTime.now());

        when(tripRepo.findAll())
                .thenReturn(List.of(trip));

        List<TripResponseDTO> trips =
                tripService.getAllTrips();

        assertNotNull(trips);

        assertEquals(1, trips.size());

        assertEquals(35,
                trips.get(0).getAvailableSeats());
    }

    @Test
    void getTripByIdTest() {

        Route route = new Route();
        route.setId(1);

        Bus bus = new Bus();
        bus.setId(1);

        Address address = new Address();
        address.setId(1);

        Driver driver = new Driver();
        driver.setId(1);

        Trip trip = new Trip();

        trip.setId(1);

        trip.setRoute(route);
        trip.setBus(bus);

        trip.setBoardingAddress(address);
        trip.setDroppingAddress(address);

        trip.setDriver1(driver);
        trip.setDriver2(driver);

        trip.setAvailableSeats(40);

        when(tripRepo.findById(1))
                .thenReturn(Optional.of(trip));

        TripResponseDTO dto =
                tripService.getTripById(1);

        assertNotNull(dto);

        assertEquals(40,
                dto.getAvailableSeats());
    }

    @Test
    void getAvailableSeatsTest() {

        Trip trip = new Trip();

        trip.setId(1);

        trip.setAvailableSeats(50);

        when(tripRepo.findById(1))
                .thenReturn(Optional.of(trip));

        Integer seats =
                tripService.getAvailableSeats(1);

        assertEquals(50, seats);
    }

    @Test
    void closeTripTest() {

        Route route = new Route();
        route.setId(1);

        Bus bus = new Bus();
        bus.setId(1);

        Address address = new Address();
        address.setId(1);

        Driver driver = new Driver();
        driver.setId(1);

        Trip trip = new Trip();

        trip.setId(1);

        trip.setRoute(route);
        trip.setBus(bus);

        trip.setBoardingAddress(address);
        trip.setDroppingAddress(address);

        trip.setDriver1(driver);
        trip.setDriver2(driver);

        trip.setAvailableSeats(40);

        when(tripRepo.findById(1))
                .thenReturn(Optional.of(trip));

        when(tripRepo.save(any(Trip.class)))
                .thenReturn(trip);

        tripService.closeTrip(1);

        assertEquals(0,
                trip.getAvailableSeats());
    }
}