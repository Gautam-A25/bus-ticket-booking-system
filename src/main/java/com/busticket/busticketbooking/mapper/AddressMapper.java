package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.entity.Address;

public class AddressMapper {

    private AddressMapper() {
    }

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