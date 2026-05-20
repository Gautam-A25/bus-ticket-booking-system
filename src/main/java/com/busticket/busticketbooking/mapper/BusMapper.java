package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Bus;

public class BusMapper {

    private BusMapper() {
    }

    public static Bus mapToEntity(
            BusRequestDTO dto,
            AgencyOffice office
    ) {
        if (dto == null) {
            return null;
        }

        Bus bus = new Bus();
        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());
        return bus;
    }

    public static BusResponseDTO mapToResponseDto(Bus bus) {
        if (bus == null) {
            return null;
        }

        BusResponseDTO responseDto = new BusResponseDTO();
        responseDto.setId(bus.getId());
        responseDto.setOfficeId(bus.getOffice() != null ? bus.getOffice().getId() : null);
        responseDto.setRegistrationNumber(bus.getRegistrationNumber());
        responseDto.setCapacity(bus.getCapacity());
        responseDto.setType(bus.getType());
        return responseDto;
    }
}
