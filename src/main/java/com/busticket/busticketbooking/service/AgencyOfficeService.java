package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AgencyOfficeService {
    AgencyOfficeResponseDTO addAgencyOffice(Integer agencyId, AgencyOfficeRequestDTO agencyOfficeRequestDTO);
    AgencyOfficeResponseDTO getAgencyOfficeById(Integer id);
    List<AgencyOfficeResponseDTO> getAgencyOfficesByAgencyId(Integer agencyId);
    Page<AgencyOfficeResponseDTO> getAgencyOfficePage(int page, int size);
    AgencyOfficeResponseDTO updateAgencyOffice(Integer id, AgencyOfficeRequestDTO agencyOfficeRequestDTO);
    String deleteAgencyOffice(Integer id);
}