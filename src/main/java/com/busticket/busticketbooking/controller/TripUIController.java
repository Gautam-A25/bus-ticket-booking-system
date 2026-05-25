package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.entity.Trip;

import com.busticket.busticketbooking.repo.TripRepo;

import com.busticket.busticketbooking.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Controller
@RequestMapping("/ui/trips")
public class TripUIController {

    @Autowired
    private TripRepo tripRepo;

    @Autowired
    private TripService tripService;

    /*
     * Display all trips
     */
    @GetMapping
    public String getAllTrips(

            @RequestParam(defaultValue = "1")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            Model model) {

        Pageable pageable =
                PageRequest.of(page - 1, size);

        Page<Trip> tripPage =
                tripRepo.findAll(pageable);

        model.addAttribute(
                "tripPage",
                tripPage);

        model.addAttribute(
                "currentPage",
                page);

        model.addAttribute(
                "pageSize",
                size);

        return "trips/list";
    }

   /*
 * Search trips
 */
@GetMapping("/search")
public String searchTrips(

        @RequestParam String fromCity,

        @RequestParam String toCity,

        Model model) {

    List<Trip> trips =
            tripRepo.findByRoute_FromCityAndRoute_ToCity(
                    fromCity,
                    toCity);

    model.addAttribute(
            "tripList",
            trips);

    return "trips/search-results";
}
    /*
     * Show create form
     */
    @GetMapping("/new")
    public String showCreateForm(
            Model model) {

        model.addAttribute(
                "trip",
                new Trip());

        model.addAttribute(
                "isEdit",
                false);

        return "trips/form";
    }

    /*
     * Create trip
     */
    @PostMapping
    public String createTrip(

            RedirectAttributes redirectAttributes,

            @RequestParam Integer routeId,

            @RequestParam Integer busId,

            @RequestParam Integer driver1Id,

            @RequestParam Integer driver2Id,

            @RequestParam Integer boardingAddressId,

            @RequestParam Integer droppingAddressId,

            @RequestParam Integer availableSeats,

            @RequestParam BigDecimal fare,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime tripDate,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime arrivalTime,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime departureTime) {

        Trip trip = new Trip();

        /*
         * Route
         */
        Route route = new Route();

        route.setId(routeId);

        trip.setRoute(route);

        /*
         * Bus
         */
        Bus bus = new Bus();

        bus.setId(busId);

        trip.setBus(bus);

        /*
         * Driver 1
         */
        Driver driver1 = new Driver();

        driver1.setId(driver1Id);

        trip.setDriver1(driver1);

        /*
         * Driver 2
         */
        Driver driver2 = new Driver();

        driver2.setId(driver2Id);

        trip.setDriver2(driver2);

        /*
         * Boarding Address
         */
        Address boardingAddress =
                new Address();

        boardingAddress.setId(
                boardingAddressId);

        trip.setBoardingAddress(
                boardingAddress);

        /*
         * Dropping Address
         */
        Address droppingAddress =
                new Address();

        droppingAddress.setId(
                droppingAddressId);

        trip.setDroppingAddress(
                droppingAddress);

        /*
         * Other fields
         */
        trip.setAvailableSeats(
                availableSeats);

        trip.setFare(
                fare);

        trip.setTripDate(
                tripDate);

        trip.setArrivalTime(
                arrivalTime);

        trip.setDepartureTime(
                departureTime);

        /*
         * Save trip
         */
        tripRepo.save(trip);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Trip created successfully."
        );

        return "redirect:/ui/trips";
    }

    /*
     * Show edit form
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(

            @PathVariable Integer id,

            Model model) {

        Trip trip =
                tripRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Trip not found"));

        model.addAttribute(
                "trip",
                trip);

        model.addAttribute(
                "tripId",
                id);

        model.addAttribute(
                "isEdit",
                true);

        return "trips/form";
    }

    /*
     * Update trip
     */
    @PostMapping("/{id}")
    public String updateTrip(

            RedirectAttributes redirectAttributes,

            @PathVariable Integer id,

            @RequestParam Integer routeId,

            @RequestParam Integer busId,

            @RequestParam Integer driver1Id,

            @RequestParam Integer driver2Id,

            @RequestParam Integer boardingAddressId,

            @RequestParam Integer droppingAddressId,

            @RequestParam Integer availableSeats,

            @RequestParam BigDecimal fare,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime tripDate,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime arrivalTime,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime departureTime) {

        Trip trip =
                tripRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Trip not found"));

        /*
         * Route
         */
        Route route = new Route();

        route.setId(routeId);

        trip.setRoute(route);

        /*
         * Bus
         */
        Bus bus = new Bus();

        bus.setId(busId);

        trip.setBus(bus);

        /*
         * Driver 1
         */
        Driver driver1 = new Driver();

        driver1.setId(driver1Id);

        trip.setDriver1(driver1);

        /*
         * Driver 2
         */
        Driver driver2 = new Driver();

        driver2.setId(driver2Id);

        trip.setDriver2(driver2);

        /*
         * Boarding Address
         */
        Address boardingAddress =
                new Address();

        boardingAddress.setId(
                boardingAddressId);

        trip.setBoardingAddress(
                boardingAddress);

        /*
         * Dropping Address
         */
        Address droppingAddress =
                new Address();

        droppingAddress.setId(
                droppingAddressId);

        trip.setDroppingAddress(
                droppingAddress);

        /*
         * Other fields
         */
        trip.setAvailableSeats(
                availableSeats);

        trip.setFare(
                fare);

        trip.setTripDate(
                tripDate);

        trip.setArrivalTime(
                arrivalTime);

        trip.setDepartureTime(
                departureTime);

        /*
         * Save updated trip
         */
        tripRepo.save(trip);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Trip updated successfully."
        );

        return "redirect:/ui/trips";
    }

    /*
     * Delete trip
     */
    @PostMapping("/{id}/delete")
    public String deleteTrip(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        try {

            tripService.deleteTrip(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Trip deleted successfully.");

        } catch (Exception ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage());
        }

        return "redirect:/ui/trips";
    }
}