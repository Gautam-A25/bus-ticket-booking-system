package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Route;

import com.busticket.busticketbooking.repo.RouteRepo;

import com.busticket.busticketbooking.service.RouteService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.data.domain.PageImpl;
import java.util.List;

@Controller
@RequestMapping("/ui/routes")
public class RouteUIController {

    @Autowired
    private RouteRepo routeRepo;
    @Autowired
    private RouteService routeService;

    /*
     * Display paginated route list
     */
    @GetMapping
    public String getAllRoutes(

            @RequestParam(defaultValue = "1")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(name = "searchId", required = false)
            Integer searchId,

            Model model) {

        if (searchId != null) {
            java.util.Optional<Route> existing = routeRepo.findById(searchId);
            if (existing.isPresent()) {
                Page<Route> routePage = new PageImpl<>(List.of(existing.get()), PageRequest.of(0, 1), 1);
                model.addAttribute("routePage", routePage);
                model.addAttribute("currentPage", 1);
                model.addAttribute("pageSize", size);
                model.addAttribute("searchId", searchId);
                return "routes/list";
            } else {
                model.addAttribute("errorMessage", "Route with ID " + searchId + " not found");
            }
        }

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

            Model model,

            RedirectAttributes redirectAttributes) {

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
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Route created successfully."
        );

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
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Route route = routeRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Route with ID " + id + " not found"));

            model.addAttribute("route", route);
            model.addAttribute("routeId", id);
            model.addAttribute("isEdit", true);

            return "routes/form";
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/ui/routes";
        }
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

            Model model,

            RedirectAttributes redirectAttributes) {

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
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Route updated successfully."
        );

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
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        try {

            String successMessage =
                    routeService.deleteRoute(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    successMessage);

        } catch (Exception ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage());
        }

        return "redirect:/ui/routes";
    }
}