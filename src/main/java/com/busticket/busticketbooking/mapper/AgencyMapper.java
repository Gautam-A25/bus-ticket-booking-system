package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.entity.Agency;

/**
 * Mapper utility class to convert between {@link Agency} entity and its DTOs.
 *
 * <p>Separates internal persistent database agency structures from client API payloads.</p>
 */
public class AgencyMapper {

    /**
     * Private constructor to prevent instantiation.
     */
    private AgencyMapper() {
    }

    /**
     * Converts an {@link Agency} entity into an {@link AgencyResponseDTO}.
     *
     * @param agency the Agency entity, can be null
     * @return the mapped AgencyResponseDTO, or null if input agency is null
     */
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

    /**
     * Converts an {@link AgencyRequestDTO} into an {@link Agency} entity.
     *
     * @param dto the AgencyRequestDTO payload, can be null
     * @return the mapped Agency entity, or null if input dto is null
     */
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