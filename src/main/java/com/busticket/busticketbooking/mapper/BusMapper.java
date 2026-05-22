package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Bus;

/*
 * Mapper class is used to convert:
 *
 * DTO -> Entity
 * Entity -> DTO
 *
 * This helps separate internal database models
 * from API request/response models.
 */
public class BusMapper {

    /*
     * Private constructor prevents object creation.
     *
     * Since all methods are static,
     * no need to create BusMapper object.
     */
    private BusMapper() {
    }

    /*
     * Converts BusRequestDTO into Bus Entity.
     *
     * This method is generally used while:
     * Creating or saving new Bus data into database.
     *
     * Parameters:
     * dto    -> Incoming request data from client
     * office -> AgencyOffice object fetched from database
     */
    public static Bus mapToEntity(
            BusRequestDTO dto,
            AgencyOffice office
    ) {

        /*
         * Null check prevents NullPointerException.
         */
        if (dto == null) {
            return null;
        }

        /*
         * Creating new Bus entity object.
         */
        Bus bus = new Bus();

        /*
         * Setting office object.
         * This creates relationship between
         * Bus and AgencyOffice.
         */
        bus.setOffice(office);

        /*
         * Setting registration number from DTO.
         */
        bus.setRegistrationNumber(dto.getRegistrationNumber());

        /*
         * Setting seating capacity.
         */
        bus.setCapacity(dto.getCapacity());

        /*
         * Setting bus type like AC, Sleeper etc.
         */
        bus.setType(dto.getType());

        /*
         * Returning fully mapped entity object.
         */
        return bus;
    }

    /*
     * Converts Bus Entity into BusResponseDTO.
     *
     * This method is generally used while:
     * Sending response back to client.
     *
     * Entity contains database object
     * DTO contains response object.
     */
    public static BusResponseDTO mapToResponseDto(Bus bus) {

        /*
         * Null check prevents NullPointerException.
         */
        if (bus == null) {
            return null;
        }

        /*
         * Creating response DTO object.
         */
        BusResponseDTO responseDto = new BusResponseDTO();

        /*
         * Setting bus ID.
         */
        responseDto.setId(bus.getId());

        /*
         * Setting office ID.
         *
         * Ternary operator checks:
         * If office exists -> return office ID
         * Else -> return null
         */
        responseDto.setOfficeId(
                bus.getOffice() != null
                        ? bus.getOffice().getId()
                        : null
        );

        /*
         * Setting registration number.
         */
        responseDto.setRegistrationNumber(
                bus.getRegistrationNumber()
        );

        /*
         * Setting seating capacity.
         */
        responseDto.setCapacity(
                bus.getCapacity()
        );

        /*
         * Setting bus type.
         */
        responseDto.setType(
                bus.getType()
        );

        /*
         * Returning final response DTO.
         */
        return responseDto;
    }
}