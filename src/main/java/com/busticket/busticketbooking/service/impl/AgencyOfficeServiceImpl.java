package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Agency;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.mapper.AgencyOfficeMapper;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.AgencyRepo;
import com.busticket.busticketbooking.service.AgencyOfficeService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgencyOfficeServiceImpl implements AgencyOfficeService {

    private final AgencyOfficeRepo agencyOfficeRepo;
    private final AgencyRepo agencyRepo;
    private final AddressRepo addressRepo;

    
    public AgencyOfficeServiceImpl(AgencyOfficeRepo agencyOfficeRepo,
                                   AgencyRepo agencyRepo,
                                   AddressRepo addressRepo) {
        this.agencyOfficeRepo = agencyOfficeRepo;
        this.agencyRepo = agencyRepo;
        this.addressRepo = addressRepo;
    }

    @Override
    public AgencyOfficeResponseDTO addAgencyOffice(Integer agencyId, AgencyOfficeRequestDTO agencyOfficeRequestDTO) {
        Agency agency = agencyRepo.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency with ID " + agencyId + " not found"));

        Address address = addressRepo.findById(agencyOfficeRequestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address with ID " + agencyOfficeRequestDTO.getAddressId() + " not found"));

        AgencyOffice agencyOffice = AgencyOfficeMapper.toEntity(agencyOfficeRequestDTO, agency, address);
        AgencyOffice savedAgencyOffice = agencyOfficeRepo.save(agencyOffice);

        return AgencyOfficeMapper.toResponseDTO(savedAgencyOffice);
    }

    @Override
    public AgencyOfficeResponseDTO getAgencyOfficeById(Integer id) {
        AgencyOffice agencyOffice = agencyOfficeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency Office with ID " + id + " not found"));
        return AgencyOfficeMapper.toResponseDTO(agencyOffice);
    }

    @Override
    public List<AgencyOfficeResponseDTO> getAgencyOfficesByAgencyId(Integer agencyId) {
        if (!agencyRepo.existsById(agencyId)) {
            throw new ResourceNotFoundException("Agency with ID " + agencyId + " not found");
        }

        return agencyOfficeRepo.findByAgency_Id(agencyId)
                .stream()
                .map(AgencyOfficeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AgencyOfficeResponseDTO updateAgencyOffice(Integer id, AgencyOfficeRequestDTO agencyOfficeRequestDTO) {
        AgencyOffice existingAgencyOffice = agencyOfficeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency Office with ID " + id + " not found"));

        Agency agency = agencyRepo.findById(existingAgencyOffice.getAgency().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Agency with ID " + existingAgencyOffice.getAgency().getId() + " not found"));

        Address address = addressRepo.findById(agencyOfficeRequestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address with ID " + agencyOfficeRequestDTO.getAddressId() + " not found"));

        existingAgencyOffice.setAgency(agency);
        existingAgencyOffice.setOfficeMail(agencyOfficeRequestDTO.getOfficeMail());
        existingAgencyOffice.setOfficeContactPersonName(agencyOfficeRequestDTO.getOfficeContactPersonName());
        existingAgencyOffice.setOfficeContactNumber(agencyOfficeRequestDTO.getOfficeContactNumber());
        existingAgencyOffice.setAddress(address);

        AgencyOffice updatedAgencyOffice = agencyOfficeRepo.save(existingAgencyOffice);
        return AgencyOfficeMapper.toResponseDTO(updatedAgencyOffice);
    }

    @Override
    public String deleteAgencyOffice(Integer id) {
        if (!agencyOfficeRepo.existsById(id)) {
            throw new ResourceNotFoundException("Agency Office with ID " + id + " not found");
        }
        agencyOfficeRepo.deleteById(id);
        return "Agency office deleted successfully";
    }
}