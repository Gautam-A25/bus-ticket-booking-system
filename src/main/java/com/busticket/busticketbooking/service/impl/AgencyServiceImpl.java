package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.entity.Agency;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.mapper.AgencyMapper;
import com.busticket.busticketbooking.repo.AgencyRepo;
import com.busticket.busticketbooking.service.AgencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepo agencyRepo;

    public AgencyServiceImpl(AgencyRepo agencyRepo) {
        this.agencyRepo = agencyRepo;
    }

    @Override
    public AgencyResponseDTO addAgency(AgencyRequestDTO agencyRequestDTO) {
        Agency agency = AgencyMapper.toEntity(agencyRequestDTO);
        Agency savedAgency = agencyRepo.save(agency);
        return AgencyMapper.toResponseDTO(savedAgency);
    }

    @Override
    public AgencyResponseDTO getAgencyById(Integer id) {
        Agency agency = agencyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency with ID " + id + " not found"));
        return AgencyMapper.toResponseDTO(agency);
    }

    @Override
    public List<AgencyResponseDTO> getAllAgencies() {
        return agencyRepo.findAll()
                .stream()
                .map(AgencyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AgencyResponseDTO> getAgencyPage(int page, int size) {
        return agencyRepo.findAll(
                        PageRequest.of(page, size, Sort.by("id").ascending())
                )
                .map(AgencyMapper::toResponseDTO);
    }

    @Override
    public AgencyResponseDTO updateAgency(Integer id, AgencyRequestDTO agencyRequestDTO) {
        Agency existingAgency = agencyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency with ID " + id + " not found"));

        existingAgency.setName(agencyRequestDTO.getName());
        existingAgency.setContactPersonName(agencyRequestDTO.getContactPersonName());
        existingAgency.setEmail(agencyRequestDTO.getEmail());
        existingAgency.setPhone(agencyRequestDTO.getPhone());

        Agency updatedAgency = agencyRepo.save(existingAgency);
        return AgencyMapper.toResponseDTO(updatedAgency);
    }

    @Override
    public String deleteAgency(Integer id) {

        Agency agency = agencyRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Agency with ID " + id + " not found"
                        ));

        String agencyDetails =
                "Agency Deleted Successfully : \n" +
                        "ID = " + agency.getId() + "\n" +
                        "Name = " + agency.getName() + "\n" +
                        "Contact Person Name = " + agency.getContactPersonName() + "\n" +
                        "Email = " + agency.getEmail() + "\n" +
                        "Phone = " + agency.getPhone();

        agencyRepo.delete(agency);

        return agencyDetails;
    }
}