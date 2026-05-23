package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.service.DriverService;
import com.busticket.busticketbooking.service.AddressService;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.mapper.AgencyOfficeMapper;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ui/drivers")
public class DriverUiController {

    private final DriverService driverService;
    private final AddressService addressService;
    private final AgencyOfficeRepo agencyOfficeRepo;

    public DriverUiController(
            DriverService driverService,
            AddressService addressService,
            AgencyOfficeRepo agencyOfficeRepo
    ) {
        this.driverService = driverService;
        this.addressService = addressService;
        this.agencyOfficeRepo = agencyOfficeRepo;
    }

    @GetMapping
    public String listDrivers(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            Model model
    ) {
        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        Page<DriverResponseDTO> driverPage = driverService.getDriverPage(safePageIndex, size);

        model.addAttribute("driverPage", driverPage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        return "driver/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("driver")) {
            model.addAttribute("driver", new DriverRequestDTO());
        }
        
        populateFormModels(model);
        model.addAttribute("isEdit", false);
        return "driver/form";
    }

    @PostMapping
    public String createDriver(
            @Valid @ModelAttribute("driver") DriverRequestDTO driver,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            populateFormModels(model);
            model.addAttribute("isEdit", false);
            return "driver/form";
        }

        try {
            driverService.createDriver(driver);
            redirectAttributes.addFlashAttribute("successMessage", "Driver created successfully.");
        } catch (Exception ex) {
            populateFormModels(model);
            model.addAttribute("isEdit", false);
            model.addAttribute("errorMessage", ex.getMessage());
            return "driver/form";
        }

        return "redirect:/ui/drivers";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            DriverResponseDTO existing = driverService.getDriverById(id);

            DriverRequestDTO driver = new DriverRequestDTO();
            driver.setName(existing.getName());
            driver.setPhone(existing.getPhone());
            driver.setLicenseNumber(existing.getLicenseNumber());
            driver.setOfficeId(existing.getOfficeId());
            driver.setAddressId(existing.getAddressId());

            model.addAttribute("driverId", id);
            model.addAttribute("driver", driver);
            populateFormModels(model);
            model.addAttribute("isEdit", true);
            return "driver/form";
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/ui/drivers";
        }
    }

    @PutMapping("/{id}")
    public String updateDriver(
            @PathVariable Integer id,
            @Valid @ModelAttribute("driver") DriverRequestDTO driver,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("driverId", id);
            populateFormModels(model);
            model.addAttribute("isEdit", true);
            return "driver/form";
        }

        try {
            driverService.updateDriver(id, driver);
            redirectAttributes.addFlashAttribute("successMessage", "Driver updated successfully.");
        } catch (Exception ex) {
            model.addAttribute("driverId", id);
            populateFormModels(model);
            model.addAttribute("isEdit", true);
            model.addAttribute("errorMessage", ex.getMessage());
            return "driver/form";
        }

        return "redirect:/ui/drivers";
    }

    @DeleteMapping("/{id}")
    public String deleteDriver(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {

            String successMessage =
                    driverService.deleteDriver(id);

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

        return "redirect:/ui/drivers";
    }

    private void populateFormModels(Model model) {
        List<AddressResponseDTO> addresses = addressService.getAllAddresses();
        List<AgencyOfficeResponseDTO> offices = agencyOfficeRepo.findAll()
                .stream()
                .map(AgencyOfficeMapper::toResponseDTO)
                .collect(Collectors.toList());

        model.addAttribute("addresses", addresses);
        model.addAttribute("offices", offices);
    }
}
