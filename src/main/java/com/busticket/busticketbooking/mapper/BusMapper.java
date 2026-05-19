package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.BusDto.BusRequestDto;
import com.busticket.busticketbooking.dto.BusDto.BusResponseDto;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Bus;

public class BusMapper {

    private BusMapper() {
    }

    public static Bus mapToEntity(
            BusRequestDto dto,
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

    public static BusResponseDto mapToResponseDto(Bus bus) {
        if (bus == null) {
            return null;
        }

        BusResponseDto responseDto = new BusResponseDto();
        responseDto.setId(bus.getId());
        responseDto.setOfficeId(bus.getOffice() != null ? bus.getOffice().getId() : null);
        responseDto.setRegistrationNumber(bus.getRegistrationNumber());
        responseDto.setCapacity(bus.getCapacity());
        responseDto.setType(bus.getType());
        return responseDto;
    }
}
