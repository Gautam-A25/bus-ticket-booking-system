package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.service.AgencyOfficeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AgencyOfficeController {

    private final AgencyOfficeService agencyOfficeService;

    public AgencyOfficeController(AgencyOfficeService agencyOfficeService) {
        this.agencyOfficeService = agencyOfficeService;
    }

    @PostMapping("/agencies/{agencyId}/offices")
    public AgencyOfficeResponseDTO addAgencyOffice(@PathVariable Integer agencyId,
                                                   @Valid @RequestBody AgencyOfficeRequestDTO agencyOfficeRequestDTO) {
        agencyOfficeRequestDTO.setAgencyId(agencyId);
        return agencyOfficeService.addAgencyOffice(agencyId, agencyOfficeRequestDTO);
    }

    @GetMapping("/agencies/{agencyId}/offices")
    public List<AgencyOfficeResponseDTO> getAgencyOfficesByAgencyId(@PathVariable Integer agencyId) {
        return agencyOfficeService.getAgencyOfficesByAgencyId(agencyId);
    }

    @GetMapping("/offices/{officeId}")
    public AgencyOfficeResponseDTO getAgencyOfficeById(@PathVariable Integer officeId) {
        return agencyOfficeService.getAgencyOfficeById(officeId);
    }

    @PutMapping("/offices/{officeId}")
    public AgencyOfficeResponseDTO updateAgencyOffice(@PathVariable Integer officeId,
                                                      @Valid @RequestBody AgencyOfficeRequestDTO agencyOfficeRequestDTO) {
        return agencyOfficeService.updateAgencyOffice(officeId, agencyOfficeRequestDTO);
    }

    @DeleteMapping("/offices/{officeId}")
    public String deleteAgencyOffice(@PathVariable Integer officeId) {
        return agencyOfficeService.deleteAgencyOffice(officeId);
    }
}