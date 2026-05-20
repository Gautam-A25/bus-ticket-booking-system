package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Driver;

public class DriverMapper {

    private DriverMapper() {
    }

    public static Driver mapToEntity(
            DriverRequestDTO dto,
            AgencyOffice office,
            Address address
    ) {
        if (dto == null) {
            return null;
        }

        Driver driver = new Driver();
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());
        driver.setOffice(office);
        driver.setAddress(address);
        return driver;
    }

    public static DriverResponseDTO mapToResponseDto(Driver driver) {
        if (driver == null) {
            return null;
        }

        DriverResponseDTO responseDto = new DriverResponseDTO();
        responseDto.setId(driver.getId());
        responseDto.setOfficeId(driver.getOffice() != null ? driver.getOffice().getId() : null);
        responseDto.setAddressId(driver.getAddress() != null ? driver.getAddress().getId() : null);
        responseDto.setLicenseNumber(driver.getLicenseNumber());
        responseDto.setName(driver.getName());
        responseDto.setPhone(driver.getPhone());
        return responseDto;
    }
}
