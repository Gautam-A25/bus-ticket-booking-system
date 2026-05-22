package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Route;

import com.busticket.busticketbooking.repo.RouteRepo;

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
@RequestMapping("/ui/routes")
public class RouteUIController {

    @Autowired
    private RouteRepo routeRepo;

    /*
     * Display paginated route list
     */
    @GetMapping
    public String getAllRoutes(

            @RequestParam(defaultValue = "1")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            Model model) {

        Pageable pageable =
                PageRequest.of(page - 1, size);

        Page<Route> routePage =
                routeRepo.findAll(pageable);

        model.addAttribute(
                "routePage",
                routePage);

        model.addAttribute(
                "currentPage",
                page);

        model.addAttribute(
                "pageSize",
                size);

        return "routes/list";
    }

    /*
     * Show create route form
     */
    @GetMapping("/new")
    public String showCreateForm(
            Model model) {

        model.addAttribute(
                "route",
                new Route());

        model.addAttribute(
                "isEdit",
                false);

        return "routes/form";
    }

    /*
     * Create new route
     */
    @PostMapping
    public String createRoute(

            @Valid
            @ModelAttribute("route")
            Route route,

            BindingResult result,

            Model model) {

        /*
         * Validation check
         */
        if (result.hasErrors()) {

            model.addAttribute(
                    "isEdit",
                    false);

            return "routes/form";
        }

        /*
         * Save route in database
         */
        routeRepo.save(route);

        /*
         * Redirect to route list
         */
        return "redirect:/ui/routes";
    }

    /*
     * Show edit form with existing values
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(

            @PathVariable Integer id,

            Model model) {

        /*
         * Fetch route from database
         */
        Route route =
                routeRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route not found"));

        /*
         * Send route data to UI
         */
        model.addAttribute(
                "route",
                route);

        model.addAttribute(
                "routeId",
                id);

        model.addAttribute(
                "isEdit",
                true);

        return "routes/form";
    }

    /*
     * Update existing route
     */
    @PostMapping("/{id}")
    public String updateRoute(

            @PathVariable Integer id,

            @Valid
            @ModelAttribute("route")
            Route route,

            BindingResult result,

            Model model) {

        /*
         * Validation check
         */
        if (result.hasErrors()) {

            model.addAttribute(
                    "isEdit",
                    true);

            model.addAttribute(
                    "routeId",
                    id);

            return "routes/form";
        }

        /*
         * Set existing route ID
         */
        route.setId(id);

        /*
         * Save updated route
         */
        routeRepo.save(route);

        /*
         * Redirect to route list
         */
        return "redirect:/ui/routes";
    }

    /*
     * Delete route by ID
     */
    @PostMapping("/{id}/delete")
    public String deleteRoute(
            @PathVariable Integer id) {

        /*
         * Delete route from database
         */
        routeRepo.deleteById(id);

        /*
         * Redirect to route list
         */
        return "redirect:/ui/routes";
    }
}