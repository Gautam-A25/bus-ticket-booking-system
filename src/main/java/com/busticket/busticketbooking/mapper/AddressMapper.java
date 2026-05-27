package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.entity.Address;

/**
 * Mapper utility class to convert between {@link Address} entity and its DTOs.
 *
 * <p>Separates internal persistent database address structures from client API payloads.</p>
 */
public class AddressMapper {

    /**
     * Private constructor to prevent instantiation.
     */
    private AddressMapper() {
    }

    /**
     * Converts an {@link Address} entity into an {@link AddressResponseDTO}.
     *
     * @param address the Address entity, can be null
     * @return the mapped AddressResponseDTO, or null if input address is null
     */
    public static AddressResponseDTO toResponseDTO(Address address) {

        if (address == null) {
            return null;
        }

        return new AddressResponseDTO(
                address.getId(),
                address.getAddress(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }

    /**
     * Converts an {@link AddressRequestDTO} into an {@link Address} entity.
     *
     * @param dto the AddressRequestDTO payload, can be null
     * @return the mapped Address entity, or null if input dto is null
     */
    public static Address toEntity(AddressRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Address address = new Address();

        address.setAddress(dto.getAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());

        return address;
    }
}