package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.BusDto.BusRequestDto;
import com.busticket.busticketbooking.dto.BusDto.BusResponseDto;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Bus;

public class BusMapper {

    public static Bus mapToEntity(BusRequestDto dto, AgencyOffice office) {
        Bus bus = new Bus();
        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());
        return bus;
    }

    public static BusResponseDto mapToResponseDto(Bus bus) {
        BusResponseDto dto = new BusResponseDto();
        dto.setId(bus.getId());
        if (bus.getOffice() != null) {
            dto.setOfficeId(bus.getOffice().getId());
        }
        dto.setRegistrationNumber(bus.getRegistrationNumber());
        dto.setCapacity(bus.getCapacity());
        dto.setType(bus.getType());
        return dto;
    }
}
