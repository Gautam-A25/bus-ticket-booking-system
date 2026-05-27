package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
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
 * Web UI Controller for managing transport agencies in the browser.
 *
 * <p>Uses Thymeleaf templates to expose visual tables, registration layouts,
 * and update forms for partner transport agency profiles.</p>
 */
@Controller
@RequestMapping("/ui/agencies")
public class AgencyUiController {

    /** Service layer for agency CRUD operations. */
    private final AgencyService agencyService;

    /**
     * Constructor injection for AgencyService dependency.
     *
     * @param agencyService the agency service layer bean
     */
    public AgencyUiController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @GetMapping
    public String listAgencies(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "searchId", required = false) Integer searchId,
            Model model
    ) {
        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        if (searchId != null) {
            try {
                AgencyResponseDTO existing = agencyService.getAgencyById(searchId);
                Page<AgencyResponseDTO> agencyPage = new PageImpl<>(List.of(existing), org.springframework.data.domain.PageRequest.of(0, 1), 1);
                model.addAttribute("agencyPage", agencyPage);
                model.addAttribute("currentPage", 1);
                model.addAttribute("pageSize", size);
                model.addAttribute("searchId", searchId);
                return "agency/list";
            } catch (ResourceNotFoundException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }

        Page<AgencyResponseDTO> agencyPage = agencyService.getAgencyPage(safePageIndex, size);

        model.addAttribute("agencyPage", agencyPage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        return "agency/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("agency")) {
            model.addAttribute("agency", new AgencyRequestDTO());
        }
        model.addAttribute("isEdit", false);
        return "agency/form";
    }

    @PostMapping
    public String createAgency(
            @Valid @ModelAttribute("agency") AgencyRequestDTO agency,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "agency/form";
        }

        agencyService.addAgency(agency);
        redirectAttributes.addFlashAttribute("successMessage", "Agency created successfully.");
        return "redirect:/ui/agencies";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            AgencyResponseDTO existing = agencyService.getAgencyById(id);

            AgencyRequestDTO agency = new AgencyRequestDTO(
                    existing.getName(),
                    existing.getContactPersonName(),
                    existing.getEmail(),
                    existing.getPhone()
            );

            model.addAttribute("agencyId", id);
            model.addAttribute("agency", agency);
            model.addAttribute("isEdit", true);
            return "agency/form";
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/ui/agencies";
        }
    }

    @PutMapping("/{id}")
    public String updateAgency(
            @PathVariable Integer id,
            @Valid @ModelAttribute("agency") AgencyRequestDTO agency,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("agencyId", id);
            model.addAttribute("isEdit", true);
            return "agency/form";
        }

        try {
            agencyService.updateAgency(id, agency);
            redirectAttributes.addFlashAttribute("successMessage", "Agency updated successfully.");
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/ui/agencies";
    }

    @DeleteMapping("/{id}")
    public String deleteAgency(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {

            String successMessage =
                    agencyService.deleteAgency(id);

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

        return "redirect:/ui/agencies";
    }
}