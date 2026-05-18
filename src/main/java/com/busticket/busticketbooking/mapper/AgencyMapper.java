package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.agencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.agencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.entity.Agency;

public class AgencyMapper {

    private AgencyMapper() {
    }

    public static AgencyResponseDTO toResponseDTO(Agency agency) {

        if (agency == null) {
            return null;
        }

        return new AgencyResponseDTO(
                agency.getId(),
                agency.getName(),
                agency.getContactPersonName(),
                agency.getEmail(),
                agency.getPhone()
        );
    }

    public static Agency toEntity(AgencyRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Agency agency = new Agency();

        agency.setName(dto.getName());
        agency.setContactPersonName(dto.getContactPersonName());
        agency.setEmail(dto.getEmail());
        agency.setPhone(dto.getPhone());

        return agency;
    }
}