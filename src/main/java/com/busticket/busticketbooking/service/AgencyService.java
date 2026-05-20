package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;

import java.util.List;

public interface AgencyService {
    AgencyResponseDTO addAgency(AgencyRequestDTO agencyRequestDTO);
    AgencyResponseDTO getAgencyById(Integer id);
    List<AgencyResponseDTO> getAllAgencies();
    AgencyResponseDTO updateAgency(Integer id, AgencyRequestDTO agencyRequestDTO);
    String deleteAgency(Integer id);
}