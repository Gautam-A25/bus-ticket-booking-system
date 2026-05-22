package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AgencyService {
    AgencyResponseDTO addAgency(AgencyRequestDTO agencyRequestDTO);
    AgencyResponseDTO getAgencyById(Integer id);
    List<AgencyResponseDTO> getAllAgencies();
    Page<AgencyResponseDTO> getAgencyPage(int page, int size);
    AgencyResponseDTO updateAgency(Integer id, AgencyRequestDTO agencyRequestDTO);
    String deleteAgency(Integer id);
}