package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Trip;

import com.busticket.busticketbooking.repo.TripRepo;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ui/trips")
public class TripUIController {

    @Autowired
    private TripRepo tripRepo;

    /*
     * Display paginated trip list
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

            @Valid
            @ModelAttribute("trip")
            Trip trip,

            BindingResult result,

            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "isEdit",
                    false);

            return "trips/form";
        }

        tripRepo.save(trip);

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

            @PathVariable Integer id,

            @Valid
            @ModelAttribute("trip")
            Trip trip,

            BindingResult result,

            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "tripId",
                    id);

            model.addAttribute(
                    "isEdit",
                    true);

            return "trips/form";
        }

        trip.setId(id);

        tripRepo.save(trip);

        return "redirect:/ui/trips";
    }

    /*
     * Delete trip
     */
    @PostMapping("/{id}/delete")
    public String deleteTrip(
            @PathVariable Integer id) {

        tripRepo.deleteById(id);

        return "redirect:/ui/trips";
    }
}