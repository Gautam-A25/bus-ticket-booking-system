package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.service.AgencyOfficeService;
import com.busticket.busticketbooking.service.BusService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing regional agency branch offices.
 *
 * <p>Handles branch registration nested under parent agencies, along with branch detail updates,
 * deactivation, and vehicle allocation lookups.</p>
 */
@RestController
@RequestMapping("/api/v1")
public class AgencyOfficeController {

    /** Service layer for agency office business logic operations. */
    private final AgencyOfficeService agencyOfficeService;

    /** Service layer for bus asset allocation operations. */
    private final BusService busService;

    /**
     * Constructor injection for AgencyOfficeController dependencies.
     *
     * @param agencyOfficeService the agency office service layer bean
     * @param busService          the bus service layer bean
     */
    public AgencyOfficeController(AgencyOfficeService agencyOfficeService, BusService busService) {
        this.agencyOfficeService = agencyOfficeService;
        this.busService = busService;
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

    @GetMapping("/offices/{officeId}/buses")
    public List<BusResponseDTO> getBusesByOffice(@PathVariable Integer officeId) {
        return busService.getBusesByOffice(officeId);
    }
}