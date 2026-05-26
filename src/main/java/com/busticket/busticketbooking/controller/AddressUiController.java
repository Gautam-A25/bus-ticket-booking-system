package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.service.AddressService;
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
import org.springframework.data.domain.PageImpl;
import java.util.List;

/**
 * Web UI Controller that handles web-based front-end requests for Address profiles.
 *
 * <p>Uses Thymeleaf templates (e.g. {@code address/list}, {@code address/form}) to display,
 * create, update, and delete address profiles from an active session.</p>
 */
@Controller
@RequestMapping("/ui/addresses")
public class AddressUiController {

    /** Service layer for address operations. */
    private final AddressService addressService;

    /**
     * Constructor injection for AddressService dependency.
     *
     * @param addressService the address service layer bean
     */
    public AddressUiController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String listAddresses(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "searchId", required = false) Integer searchId,
            Model model
    ) {
        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        if (searchId != null) {
            try {
                AddressResponseDTO existing = addressService.getAddressById(searchId);
                Page<AddressResponseDTO> addressPage = new PageImpl<>(List.of(existing), org.springframework.data.domain.PageRequest.of(0, 1), 1);
                model.addAttribute("addressPage", addressPage);
                model.addAttribute("currentPage", 1);
                model.addAttribute("pageSize", size);
                model.addAttribute("searchId", searchId);
                return "address/list";
            } catch (ResourceNotFoundException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }

        Page<AddressResponseDTO> addressPage = addressService.getAddressPage(safePageIndex, size);

        model.addAttribute("addressPage", addressPage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        return "address/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("address")) {
            model.addAttribute("address", new AddressRequestDTO());
        }
        model.addAttribute("isEdit", false);
        return "address/form";
    }

    @PostMapping
    public String createAddress(
            @Valid @ModelAttribute("address") AddressRequestDTO address,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "address/form";
        }

        addressService.addAddress(address);
        redirectAttributes.addFlashAttribute("successMessage", "Address created successfully.");
        return "redirect:/ui/addresses";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            AddressResponseDTO existing = addressService.getAddressById(id);

            AddressRequestDTO address = new AddressRequestDTO();
            address.setAddress(existing.getAddress());
            address.setCity(existing.getCity());
            address.setState(existing.getState());
            address.setZipCode(existing.getZipCode());

            model.addAttribute("addressId", id);
            model.addAttribute("address", address);
            model.addAttribute("isEdit", true);
            return "address/form";
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/ui/addresses";
        }
    }

    @PutMapping("/{id}")
    public String updateAddress(
            @PathVariable Integer id,
            @Valid @ModelAttribute("address") AddressRequestDTO address,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addressId", id);
            model.addAttribute("isEdit", true);
            return "address/form";
        }

        try {
            addressService.updateAddress(id, address);
            redirectAttributes.addFlashAttribute("successMessage", "Address updated successfully.");
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/ui/addresses";
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {

            String message = addressService.deleteAddress(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    message
            );

        } catch (ResourceNotFoundException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
        }

        return "redirect:/ui/addresses";
    }
}