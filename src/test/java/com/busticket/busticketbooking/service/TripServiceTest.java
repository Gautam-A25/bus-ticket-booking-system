// Total tests: 10
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.TripDto.TripRequestDto;
import com.busticket.busticketbooking.dto.TripDto.TripResponseDto;
import com.busticket.busticketbooking.entity.*;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.impl.TripServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    private TripRepo tripRepo;

    @InjectMocks
    private TripServiceImpl tripService;

    private TripRequestDto requestDto;
    private Trip trip;
    private Route route;
    private Bus bus;
    private Address boarding;
    private Address dropping;
    private Driver driver1;
    private Driver driver2;

    @BeforeEach
    public void setUp() {
        LocalDateTime departure = LocalDateTime.of(2026, 5, 20, 8, 0);
        LocalDateTime arrival = LocalDateTime.of(2026, 5, 20, 12, 0);
        LocalDateTime tripDate = LocalDateTime.of(2026, 5, 20, 0, 0);

        requestDto = new TripRequestDto();
        requestDto.setRouteId(1);
        requestDto.setBusId(2);
        requestDto.setBoardingAddressId(3);
        requestDto.setDroppingAddressId(4);
        requestDto.setDriver1Id(5);
        requestDto.setDriver2Id(6);
        requestDto.setDepartureTime(departure);
        requestDto.setArrivalTime(arrival);
        requestDto.setAvailableSeats(35);
        requestDto.setFare(new BigDecimal("500.00"));
        requestDto.setTripDate(tripDate);

        route = new Route();
        route.setId(1);

        bus = new Bus();
        bus.setId(2);

        boarding = new Address();
        boarding.setId(3);

        dropping = new Address();
        dropping.setId(4);

        driver1 = new Driver();
        driver1.setId(5);

        driver2 = new Driver();
        driver2.setId(6);

        trip = new Trip();
        trip.setId(11);
        trip.setRoute(route);
        trip.setBus(bus);
        trip.setBoardingAddress(boarding);
        trip.setDroppingAddress(dropping);
        trip.setDriver1(driver1);
        trip.setDriver2(driver2);
        trip.setDepartureTime(departure);
        trip.setArrivalTime(arrival);
        trip.setAvailableSeats(35);
        trip.setFare(new BigDecimal("500.00"));
        trip.setTripDate(tripDate);
    }

    /**
     * 1. testAddTrip_Success - Verify that a trip is created successfully when valid details are supplied.
     */
    @Test
    public void testAddTrip_Success() {
        when(tripRepo.save(any(Trip.class))).thenReturn(trip);

        TripResponseDto response = tripService.addTrip(requestDto);

        assertNotNull(response);
        assertEquals(11, response.getId());
        assertEquals(35, response.getAvailableSeats());
    }

    /**
     * 2. testGetTripById_Success - Verify that a trip is successfully retrieved by ID.
     */
    @Test
    public void testGetTripById_Success() {
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        TripResponseDto response = tripService.getTripById(11);
        assertNotNull(response);
        assertEquals(11, response.getId());
    }

    /**
     * 3. testGetTripById_NotFound_ThrowsException - Verify that requesting a missing ID throws RuntimeException.
     */
    @Test
    public void testGetTripById_NotFound_ThrowsException() {
        when(tripRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> tripService.getTripById(999));
    }

    /**
     * 4. testGetAllTrips_NotEmpty - Verify that list of trips is successfully retrieved.
     */
    @Test
    public void testGetAllTrips_NotEmpty() {
        when(tripRepo.findAll()).thenReturn(Arrays.asList(trip));
        List<TripResponseDto> response = tripService.getAllTrips();
        assertEquals(1, response.size());
    }

    /**
     * 5. testGetAllTrips_Empty - Verify that an empty list is handled properly.
     */
    @Test
    public void testGetAllTrips_Empty() {
        when(tripRepo.findAll()).thenReturn(Collections.emptyList());
        List<TripResponseDto> response = tripService.getAllTrips();
        assertTrue(response.isEmpty());
    }

    /**
     * 6. testSearchTrips_Success - Verify searching trips by from/to cities.
     */
    @Test
    public void testSearchTrips_Success() {
        when(tripRepo.findByRoute_FromCityAndRoute_ToCity("Mumbai", "Pune")).thenReturn(Arrays.asList(trip));
        List<TripResponseDto> response = tripService.searchTrips("Mumbai", "Pune");
        assertEquals(1, response.size());
    }

    /**
     * 7. testGetAvailableSeats_Success - Verify getting available seats.
     */
    @Test
    public void testGetAvailableSeats_Success() {
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        Integer seats = tripService.getAvailableSeats(11);
        assertEquals(35, seats);
    }

    /**
     * 8. testCloseTrip_Success - Verify closing trip seats to 0.
     */
    @Test
    public void testCloseTrip_Success() {
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        when(tripRepo.save(any(Trip.class))).thenReturn(trip);

        assertAll(() -> tripService.closeTrip(11));
        assertEquals(0, trip.getAvailableSeats());
    }

    /**
     * 9. testUpdateTrip_Success - Verify updating trip details successfully.
     */
    @Test
    public void testUpdateTrip_Success() {
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        when(tripRepo.save(any(Trip.class))).thenReturn(trip);

        TripResponseDto response = tripService.updateTrip(11, requestDto);
        assertNotNull(response);
        assertEquals(11, response.getId());
    }

    /**
     * 10. testUpdateTrip_NotFound_ThrowsException - Verify updating a missing ID throws RuntimeException.
     */
    @Test
    public void testUpdateTrip_NotFound_ThrowsException() {
        when(tripRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> tripService.updateTrip(999, requestDto));
    }
}
