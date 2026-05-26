package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.service.AgencyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing transport agency profiles.
 *
 * <p>Exposes RESTful endpoints for registering, fetching, updating, and deactivating
 * partner agencies in the passenger transport system.</p>
 */
@RestController
@RequestMapping("/api/v1/agencies")
public class AgencyController {

    /** Service layer for agency business logic operations. */
    private final AgencyService agencyService;

    /**
     * Constructor injection for AgencyService dependency.
     *
     * @param agencyService the agency service layer bean
     */
    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @PostMapping
    public AgencyResponseDTO addAgency(@Valid @RequestBody AgencyRequestDTO agencyRequestDTO) {
        return agencyService.addAgency(agencyRequestDTO);
    }

    @GetMapping("/{id}")
    public AgencyResponseDTO getAgencyById(@PathVariable Integer id) {
        return agencyService.getAgencyById(id);
    }

    @GetMapping
    public List<AgencyResponseDTO> getAllAgencies() {
        return agencyService.getAllAgencies();
    }

    @PutMapping("/{id}")
    public AgencyResponseDTO updateAgency(@PathVariable Integer id,
                                          @Valid @RequestBody AgencyRequestDTO agencyRequestDTO) {
        return agencyService.updateAgency(id, agencyRequestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteAgency(@PathVariable Integer id) {
        return agencyService.deleteAgency(id);
    }
}