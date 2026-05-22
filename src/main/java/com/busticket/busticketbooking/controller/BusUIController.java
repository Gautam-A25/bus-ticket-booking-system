package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.service.BusService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String listBuses(Model model) {

        model.addAttribute(
                "buses",
                busService.getAllBuses()
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
            Model model
    ) {

        if (result.hasErrors()) {

            model.addAttribute("isEdit", false);

            return "buses/form";
        }

        busService.createBus(dto);

        return "redirect:/ui/buses";
    }

    /*
     * Open edit form
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model
    ) {

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
    }

    /*
     * Update bus
     */
    @PostMapping("/{id}")
    public String updateBus(
            @PathVariable Integer id,
            @Valid @ModelAttribute("bus") BusRequestDTO dto,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {

            model.addAttribute("busId", id);
            model.addAttribute("isEdit", true);

            return "buses/form";
        }

        busService.updateBus(id, dto);

        return "redirect:/ui/buses";
    }

    /*
     * Delete bus
     */
    @GetMapping("/{id}/delete")
    public String deleteBus(@PathVariable Integer id) {

        busService.deleteBus(id);

        return "redirect:/ui/buses";
    }
}