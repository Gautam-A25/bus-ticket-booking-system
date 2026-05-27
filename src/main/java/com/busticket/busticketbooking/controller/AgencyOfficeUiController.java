package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.service.AddressService;
import com.busticket.busticketbooking.service.AgencyOfficeService;
import com.busticket.busticketbooking.service.AgencyService;
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
 * Web UI Controller for managing regional Agency Offices in the browser.
 *
 * <p>Exposes interactive Thymeleaf-based layouts for viewing, registering, and deactivating
 * regional branches under partner agencies.</p>
 */
@Controller
@RequestMapping("/ui/offices")
public class AgencyOfficeUiController {

    /** Service layer for agency office database operations. */
    private final AgencyOfficeService agencyOfficeService;
    private final AgencyService agencyService;
    private final AddressService addressService;

    /**
     * Constructor injection for dependency services.
     *
     * @param agencyOfficeService the agency office service layer bean
     * @param agencyService       the agency service layer bean
     * @param addressService      the address service layer bean
     */
    public AgencyOfficeUiController(
            AgencyOfficeService agencyOfficeService,
            AgencyService agencyService,
            AddressService addressService
    ) {
        this.agencyOfficeService = agencyOfficeService;
        this.agencyService = agencyService;
        this.addressService = addressService;
    }

    @GetMapping
    public String listOffices(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "searchId", required = false) Integer searchId,
            Model model
    ) {
        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        if (searchId != null) {
            try {
                AgencyOfficeResponseDTO existing = agencyOfficeService.getAgencyOfficeById(searchId);
                Page<AgencyOfficeResponseDTO> officePage = new PageImpl<>(List.of(existing), org.springframework.data.domain.PageRequest.of(0, 1), 1);
                model.addAttribute("officePage", officePage);
                model.addAttribute("currentPage", 1);
                model.addAttribute("pageSize", size);
                model.addAttribute("searchId", searchId);
                return "offices/list";
            } catch (ResourceNotFoundException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }

        Page<AgencyOfficeResponseDTO> officePage = agencyOfficeService.getAgencyOfficePage(safePageIndex, size);

        model.addAttribute("officePage", officePage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        return "offices/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("office")) {
            model.addAttribute("office", new AgencyOfficeRequestDTO());
        }
        loadReferenceData(model);
        model.addAttribute("isEdit", false);
        return "offices/form";
    }

    @PostMapping
    public String createOffice(
            @Valid @ModelAttribute("office") AgencyOfficeRequestDTO office,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (office.getAgencyId() == null) {
            bindingResult.rejectValue("agencyId", "agencyId", "Agency selection is required");
        }

        if (bindingResult.hasErrors()) {
            loadReferenceData(model);
            model.addAttribute("isEdit", false);
            return "offices/form";
        }

        agencyOfficeService.addAgencyOffice(office.getAgencyId(), office);
        redirectAttributes.addFlashAttribute("successMessage", "Agency office created successfully.");
        return "redirect:/ui/offices";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            AgencyOfficeResponseDTO existing = agencyOfficeService.getAgencyOfficeById(id);

            AgencyOfficeRequestDTO office = new AgencyOfficeRequestDTO(
                    existing.getAgencyId(),
                    existing.getOfficeMail(),
                    existing.getOfficeContactPersonName(),
                    existing.getOfficeContactNumber(),
                    existing.getAddressId()
            );

            model.addAttribute("officeId", id);
            model.addAttribute("office", office);
            loadReferenceData(model);
            model.addAttribute("isEdit", true);
            return "offices/form";
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/ui/offices";
        }
    }

    @PutMapping("/{id}")
    public String updateOffice(
            @PathVariable Integer id,
            @Valid @ModelAttribute("office") AgencyOfficeRequestDTO office,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (office.getAgencyId() == null) {
            bindingResult.rejectValue("agencyId", "agencyId", "Agency selection is required");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("officeId", id);
            loadReferenceData(model);
            model.addAttribute("isEdit", true);
            return "offices/form";
        }

        try {
            agencyOfficeService.updateAgencyOffice(id, office);
            redirectAttributes.addFlashAttribute("successMessage", "Agency office updated successfully.");
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/ui/offices";
    }

    @DeleteMapping("/{id}")
    public String deleteOffice(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {

            String successMessage =
                    agencyOfficeService.deleteAgencyOffice(id);

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

        return "redirect:/ui/offices";
    }

    private void loadReferenceData(Model model) {
        List<AgencyResponseDTO> agencies = agencyService.getAllAgencies();
        List<AddressResponseDTO> addresses = addressService.getAllAddresses();

        model.addAttribute("agencies", agencies);
        model.addAttribute("addresses", addresses);
    }
}