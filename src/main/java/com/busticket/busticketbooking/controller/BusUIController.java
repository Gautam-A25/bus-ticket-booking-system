package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.service.BusService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.List;

@Controller
@RequestMapping("/ui/buses")
public class BusUIController {

    private final BusService busService;

    public BusUIController(BusService busService) {
        this.busService = busService;
    }

    /*
     * Show all buses
     */
    @GetMapping
    public String listBuses(

            @RequestParam(name = "page", defaultValue = "1")
            int page,

            @RequestParam(name = "size", defaultValue = "6")
            int size,

            @RequestParam(name = "searchId", required = false)
            Integer searchId,

            Model model
    ) {

        int requestedPage = Math.max(page, 1);

        int safePageIndex = requestedPage - 1;

        if (searchId != null) {
            try {
                BusResponseDTO existing = busService.getBusById(searchId);
                Page<BusResponseDTO> busPage = new PageImpl<>(List.of(existing), org.springframework.data.domain.PageRequest.of(0, 1), 1);
                model.addAttribute("busPage", busPage);
                model.addAttribute("currentPage", 1);
                model.addAttribute("pageSize", size);
                model.addAttribute("searchId", searchId);
                return "buses/list";
            } catch (ResourceNotFoundException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }

        Page<BusResponseDTO> busPage =
                busService.getBusPage(
                        safePageIndex,
                        size
                );

        model.addAttribute(
                "busPage",
                busPage
        );

        model.addAttribute(
                "currentPage",
                requestedPage
        );

        model.addAttribute(
                "pageSize",
                size
        );

        return "buses/list";
    }

    /*
     * Open create form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute(
                "bus",
                new BusRequestDTO()
        );

        model.addAttribute(
                "isEdit",
                false
        );

        return "buses/form";
    }

    /*
     * Save new bus
     */
    @PostMapping
    public String saveBus(
            @Valid @ModelAttribute("bus") BusRequestDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            model.addAttribute("isEdit", false);

            return "buses/form";
        }

        busService.createBus(dto);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Bus created successfully."
        );

        return "redirect:/ui/buses";
    }

    /*
     * Open edit form
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            BusResponseDTO bus = busService.getBusById(id);

            BusRequestDTO dto = new BusRequestDTO();

            if (bus.getOfficeId() != null) {
                dto.setOfficeId(bus.getOfficeId());
            }

            dto.setRegistrationNumber(bus.getRegistrationNumber());
            dto.setCapacity(bus.getCapacity());
            dto.setType(bus.getType());

            model.addAttribute("bus", dto);
            model.addAttribute("busId", id);
            model.addAttribute("isEdit", true);

            return "buses/form";
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/ui/buses";
        }
    }

    /*
     * Update bus
     */
    @PutMapping("/{id}")
    public String updateBus(
            @PathVariable Integer id,
            @Valid @ModelAttribute("bus") BusRequestDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            model.addAttribute("busId", id);
            model.addAttribute("isEdit", true);

            return "buses/form";
        }

        try {

            busService.updateBus(id, dto);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Bus updated successfully."
            );

        } catch (ResourceNotFoundException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
        }

        return "redirect:/ui/buses";
    }

    /*
     * Delete bus
     */
    @DeleteMapping("/{id}")
    public String deleteBus(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {

        try {

            String successMessage =
                    busService.deleteBus(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    successMessage
            );

        } catch (ResourceNotFoundException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
        }

        return "redirect:/ui/buses";
    }
}