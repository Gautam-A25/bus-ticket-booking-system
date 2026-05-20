package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.service.AgencyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agencies")
public class AgencyController {

    private final AgencyService agencyService;

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