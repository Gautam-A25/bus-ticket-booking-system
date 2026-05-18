package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.agencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.agencyOfficeDTO.AgencyOfficeResponseDTO;

import java.util.List;

public interface AgencyOfficeService {
    AgencyOfficeResponseDTO addAgencyOffice(Integer agencyId, AgencyOfficeRequestDTO agencyOfficeRequestDTO);
    AgencyOfficeResponseDTO getAgencyOfficeById(Integer id);
    List<AgencyOfficeResponseDTO> getAgencyOfficesByAgencyId(Integer agencyId);
    AgencyOfficeResponseDTO updateAgencyOffice(Integer id, AgencyOfficeRequestDTO agencyOfficeRequestDTO);
    String deleteAgencyOffice(Integer id);
}