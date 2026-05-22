package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.service.AgencyOfficeService;
import com.busticket.busticketbooking.service.BusService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AgencyOfficeController {

    private final AgencyOfficeService agencyOfficeService;
    private final BusService busService;

    public AgencyOfficeController(AgencyOfficeService agencyOfficeService, BusService busService) {
        this.agencyOfficeService = agencyOfficeService;
        this.busService = busService;
    }

    @PostMapping("/agencies/{agencyId}/offices")
    public AgencyOfficeResponseDTO addAgencyOffice(@PathVariable Integer agencyId,
                                                   @Valid @RequestBody AgencyOfficeRequestDTO agencyOfficeRequestDTO) {
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

    @GetMapping("/offices/{officeId}/buses")
    public List<BusResponseDTO> getBusesByOffice(@PathVariable Integer officeId) {
        return busService.getBusesByOffice(officeId);
    }
}