package com.busticket.busticketbooking.controller;
import jakarta.transaction.Transactional;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.TripRepo;

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
        private BookingRepo bookingRepo;

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

        @RequestParam Integer routeId,

        @RequestParam Integer busId,

        @RequestParam Integer driver1Id,

        @RequestParam Integer driver2Id,

        @RequestParam Integer boardingAddressId,

        @RequestParam Integer droppingAddressId,

        @RequestParam Integer availableSeats,

        @RequestParam BigDecimal fare,

        @RequestParam LocalDateTime tripDate,

        @RequestParam LocalDateTime arrivalTime,

        @RequestParam LocalDateTime departureTime,

        Model model) {

    try {

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
        Address boardingAddress = new Address();
        boardingAddress.setId(boardingAddressId);
        trip.setBoardingAddress(boardingAddress);

        /*
         * Dropping Address
         */
        Address droppingAddress = new Address();
        droppingAddress.setId(droppingAddressId);
        trip.setDroppingAddress(droppingAddress);

        /*
         * Other fields
         */
        trip.setAvailableSeats(availableSeats);

        trip.setFare(fare);

        trip.setTripDate(tripDate);

        trip.setArrivalTime(arrivalTime);

        trip.setDepartureTime(departureTime);

        /*
         * Save
         */
        tripRepo.save(trip);

        return "redirect:/ui/trips";

    } catch (Exception ex) {

        model.addAttribute(
                "errorMessage",
                "Invalid IDs entered. Please check Route, Bus, Driver and Address IDs.");

        model.addAttribute(
                "trip",
                new Trip());

        model.addAttribute(
                "isEdit",
                false);

        return "trips/form";
    }
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

        @PathVariable Integer id,

        @RequestParam Integer routeId,

        @RequestParam Integer busId,

        @RequestParam Integer driver1Id,

        @RequestParam Integer driver2Id,

        @RequestParam Integer boardingAddressId,

        @RequestParam Integer droppingAddressId,

        @RequestParam Integer availableSeats,

        @RequestParam BigDecimal fare,

        @RequestParam LocalDateTime tripDate,

        @RequestParam LocalDateTime arrivalTime,

        @RequestParam LocalDateTime departureTime,

        Model model) {

    try {

        Trip trip =
                tripRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Trip not found"));

        Route route = new Route();
        route.setId(routeId);
        trip.setRoute(route);

        Bus bus = new Bus();
        bus.setId(busId);
        trip.setBus(bus);

        Driver driver1 = new Driver();
        driver1.setId(driver1Id);
        trip.setDriver1(driver1);

        Driver driver2 = new Driver();
        driver2.setId(driver2Id);
        trip.setDriver2(driver2);

        Address boardingAddress = new Address();
        boardingAddress.setId(boardingAddressId);
        trip.setBoardingAddress(boardingAddress);

        Address droppingAddress = new Address();
        droppingAddress.setId(droppingAddressId);
        trip.setDroppingAddress(droppingAddress);

        trip.setAvailableSeats(availableSeats);

        trip.setFare(fare);

        trip.setTripDate(tripDate);

        trip.setArrivalTime(arrivalTime);

        trip.setDepartureTime(departureTime);

        tripRepo.save(trip);

        return "redirect:/ui/trips";

    } catch (Exception ex) {

        /*
         * IMPORTANT
         * reload existing trip
         */
        Trip trip =
                tripRepo.findById(id)
                        .orElse(new Trip());

        model.addAttribute(
                "errorMessage",
                "Invalid IDs entered. Please check Route, Bus, Driver and Address IDs.");

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
}

    /*
     * Delete trip
     */
@PostMapping("/{id}/delete")
public String deleteTrip(

        @PathVariable Integer id,

        RedirectAttributes redirectAttributes) {

    try {

        /*
         * Delete payments
         */
        bookingRepo.deletePaymentsByTripId(id);

        /*
         * Delete reviews
         */
        bookingRepo.deleteReviewsByTripId(id);

        /*
         * Delete bookings
         */
        bookingRepo.deleteBookingsByTripId(id);

        /*
         * Delete trip
         */
        tripRepo.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Trip deleted successfully.");

    } catch (Exception ex) {

        ex.printStackTrace();

        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "Unable to delete trip.");
    }

    return "redirect:/ui/trips";
}
}
