package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.DriverDto.DriverRequestDto;
import com.busticket.busticketbooking.dto.DriverDto.DriverResponseDto;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Driver;

public class DriverMapper {

    public static Driver mapToEntity(DriverRequestDto dto, AgencyOffice office, Address address) {
        Driver driver = new Driver();
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());
        driver.setOffice(office);
        driver.setAddress(address);
        return driver;
    }

    public static DriverResponseDto mapToResponseDto(Driver driver) {
        DriverResponseDto dto = new DriverResponseDto();
        dto.setId(driver.getId());
        if (driver.getOffice() != null) {
            dto.setOfficeId(driver.getOffice().getId());
        }
        if (driver.getAddress() != null) {
            dto.setAddressId(driver.getAddress().getId());
        }
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setName(driver.getName());
        dto.setPhone(driver.getPhone());
        return dto;
    }
}
