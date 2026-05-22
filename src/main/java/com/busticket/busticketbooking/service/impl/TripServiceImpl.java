package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDTO;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDTO;
import com.busticket.busticketbooking.dto.TripDTO.SeatAvailabilityDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.service.TripService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepo tripRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public List<TripResponseDTO> getAllTrips() {

        List<Trip> trips = tripRepo.findAll();

        return trips.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TripResponseDTO getTripById(Integer id) {

        Trip trip = tripRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trip not found with id: " + id));

        return mapToResponseDto(trip);
    }

    @Override
    public TripResponseDTO addTrip(TripRequestDTO tripRequestDto) {

        Trip trip = mapToEntity(tripRequestDto);

        Trip savedTrip = tripRepo.save(trip);

        return mapToResponseDto(savedTrip);
    }

    @Override
    public TripResponseDTO updateTrip(Integer id,
                                      TripRequestDTO tripRequestDto) {

        Trip existingTrip = tripRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trip not found with id: " + id));

        existingTrip.setRoute(createRoute(tripRequestDto.getRouteId()));
        existingTrip.setBus(createBus(tripRequestDto.getBusId()));
        existingTrip.setBoardingAddress(
                createAddress(tripRequestDto.getBoardingAddressId()));

        existingTrip.setDroppingAddress(
                createAddress(tripRequestDto.getDroppingAddressId()));

        existingTrip.setDriver1(
                createDriver(tripRequestDto.getDriver1Id()));

        existingTrip.setDriver2(
                createDriver(tripRequestDto.getDriver2Id()));

        existingTrip.setDepartureTime(
                tripRequestDto.getDepartureTime());

        existingTrip.setArrivalTime(
                tripRequestDto.getArrivalTime());

        existingTrip.setAvailableSeats(
                tripRequestDto.getAvailableSeats());

        existingTrip.setFare(
                tripRequestDto.getFare());

        existingTrip.setTripDate(
                tripRequestDto.getTripDate());

        Trip updatedTrip = tripRepo.save(existingTrip);

        return mapToResponseDto(updatedTrip);
    }

    @Override
    public String closeTrip(Integer id) {

        Trip trip = tripRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Trip not found with id: " + id
                        ));

        trip.setAvailableSeats(0);

        tripRepo.save(trip);

        return "Trip Closed Successfully : \n" +
                "ID = " + trip.getId() + "\n" +
                "Route = " + trip.getRoute() + "\n" +
                "Bus = " + trip.getBus() + "\n" +
                "Boarding Address = " + trip.getBoardingAddress() + "\n" +
                "Dropping Address = " + trip.getDroppingAddress() + "\n" +
                "Departure Time = " + trip.getDepartureTime() + "\n" +
                "Arrival Time = " + trip.getArrivalTime() + "\n" +
                "Driver 1 = " + trip.getDriver1() + "\n" +
                "Driver 2 = " + trip.getDriver2() + "\n" +
                "Available Seats = " + trip.getAvailableSeats() + "\n" +
                "Fare = " + trip.getFare() + "\n" +
                "Trip Date = " + trip.getTripDate();
    }

    @Override
    public List<TripResponseDTO> searchTrips(String fromCity,
                                             String toCity) {

        List<Trip> trips =
                tripRepo.findByRoute_FromCityAndRoute_ToCity(
                        fromCity,
                        toCity);

        return trips.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripResponseDTO> searchTrips(String fromCity,
                                             String toCity,
                                             LocalDate date) {

        List<Trip> trips =
                tripRepo.findByRoute_FromCityAndRoute_ToCityAndTripDate(
                        fromCity,
                        toCity,
                        date);

        return trips.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatAvailabilityDTO> getSeatAvailability(Integer tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));
        
        Integer capacity = trip.getBus().getCapacity();
        List<Booking> bookings = bookingRepo.findByTripId(tripId);
        
        List<Integer> bookedSeats = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.Booked)
                .map(Booking::getSeatNumber)
                .toList();

        List<SeatAvailabilityDTO> availability = new ArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            String status = bookedSeats.contains(i) ? "Booked" : "Available";
            availability.add(new SeatAvailabilityDTO(i, status));
        }
        return availability;
    }

    @Override
    public List<Integer> getBookedSeats(Integer tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));
        
        List<Booking> bookings = bookingRepo.findByTripId(tripId);
        return bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.Booked)
                .map(Booking::getSeatNumber)
                .sorted()
                .toList();
    }

    @Override
    public List<Integer> getAvailableSeatList(Integer tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));
        
        Integer capacity = trip.getBus().getCapacity();
        List<Booking> bookings = bookingRepo.findByTripId(tripId);
        List<Integer> bookedSeats = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.Booked)
                .map(Booking::getSeatNumber)
                .toList();

        List<Integer> availableSeats = new ArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            if (!bookedSeats.contains(i)) {
                availableSeats.add(i);
            }
        }
        return availableSeats;
    }

    private TripResponseDTO mapToResponseDto(Trip trip) {

        TripResponseDTO dto = new TripResponseDTO();

        dto.setId(trip.getId());

        dto.setRouteId(trip.getRoute().getId());

        dto.setBusId(trip.getBus().getId());

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

        dto.setFare(
                trip.getFare());

        dto.setTripDate(
                trip.getTripDate());

        return dto;
    }

    private Trip mapToEntity(TripRequestDTO dto) {

        Trip trip = new Trip();

        trip.setRoute(createRoute(dto.getRouteId()));

        trip.setBus(createBus(dto.getBusId()));

        trip.setBoardingAddress(
                createAddress(dto.getBoardingAddressId()));

        trip.setDroppingAddress(
                createAddress(dto.getDroppingAddressId()));

        trip.setDriver1(
                createDriver(dto.getDriver1Id()));

        trip.setDriver2(
                createDriver(dto.getDriver2Id()));

        trip.setDepartureTime(dto.getDepartureTime());

        trip.setArrivalTime(dto.getArrivalTime());

        trip.setAvailableSeats(dto.getAvailableSeats());

        trip.setFare(dto.getFare());

        trip.setTripDate(dto.getTripDate());

        return trip;
    }

    private Route createRoute(Integer id) {

        Route route = new Route();

        route.setId(id);

        return route;
    }

    private Bus createBus(Integer id) {

        Bus bus = new Bus();

        bus.setId(id);

        return bus;
    }

    private Address createAddress(Integer id) {

        Address address = new Address();

        address.setId(id);

        return address;
    }

    private Driver createDriver(Integer id) {

        Driver driver = new Driver();

        driver.setId(id);

        return driver;
    }
}