package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Driver;

/*
 * Mapper class is responsible for converting:
 *
 * DTO -> Entity
 * Entity -> DTO
 *
 * This helps separate database models
 * from API request/response models.
 */
public class DriverMapper {

    /*
     * Private constructor prevents object creation.
     *
     * Since all methods are static,
     * no need to create DriverMapper object.
     */
    private DriverMapper() {
    }

    /*
     * Converts DriverRequestDTO into Driver Entity.
     *
     * Used while saving driver data into database.
     *
     * Parameters:
     * dto      -> Request data received from client
     * office   -> AgencyOffice object fetched from database
     * address  -> Address object fetched from database
     */
    public static Driver mapToEntity(
            DriverRequestDTO dto,
            AgencyOffice office,
            Address address
    ) {

        /*
         * Null check prevents NullPointerException.
         */
        if (dto == null) {
            return null;
        }

        /*
         * Creating new Driver entity object.
         */
        Driver driver = new Driver();

        /*
         * Setting driver's license number.
         */
        driver.setLicenseNumber(dto.getLicenseNumber());

        /*
         * Setting driver name.
         */
        driver.setName(dto.getName());

        /*
         * Setting driver phone number.
         */
        driver.setPhone(dto.getPhone());

        /*
         * Setting office object.
         *
         * This creates relationship between
         * Driver and AgencyOffice.
         */
        driver.setOffice(office);

        /*
         * Setting address object.
         *
         * This creates relationship between
         * Driver and Address.
         */
        driver.setAddress(address);

        /*
         * Returning fully mapped Driver entity.
         */
        return driver;
    }

    /*
     * Converts Driver Entity into DriverResponseDTO.
     *
     * Used while sending response back to client.
     *
     * Entity contains database object.
     * DTO contains API response object.
     */
    public static DriverResponseDTO mapToResponseDto(Driver driver) {

        /*
         * Null check prevents NullPointerException.
         */
        if (driver == null) {
            return null;
        }

        /*
         * Creating response DTO object.
         */
        DriverResponseDTO responseDto = new DriverResponseDTO();

        /*
         * Setting driver ID.
         */
        responseDto.setId(driver.getId());

        /*
         * Setting office ID.
         *
         * Ternary operator checks:
         * If office exists -> return office ID
         * Else -> return null
         */
        responseDto.setOfficeId(
                driver.getOffice() != null
                        ? driver.getOffice().getId()
                        : null
        );

        /*
         * Setting address ID.
         *
         * If address exists -> return address ID
         * Else -> return null
         */
        responseDto.setAddressId(
                driver.getAddress() != null
                        ? driver.getAddress().getId()
                        : null
        );

        /*
         * Setting driver's license number.
         */
        responseDto.setLicenseNumber(
                driver.getLicenseNumber()
        );

        /*
         * Setting driver name.
         */
        responseDto.setName(
                driver.getName()
        );

        /*
         * Setting driver phone number.
         */
        responseDto.setPhone(
                driver.getPhone()
        );

        /*
         * Returning final response DTO.
         */
        return responseDto;
    }
}