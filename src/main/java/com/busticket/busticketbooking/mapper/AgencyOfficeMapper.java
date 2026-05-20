package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Agency;
import com.busticket.busticketbooking.entity.AgencyOffice;

public class AgencyOfficeMapper {

    private AgencyOfficeMapper() {
    }

    public static AgencyOfficeResponseDTO toResponseDTO(AgencyOffice agencyOffice) {

        if (agencyOffice == null) {
            return null;
        }

        return new AgencyOfficeResponseDTO(
                agencyOffice.getId(),
                agencyOffice.getAgency() != null
                        ? agencyOffice.getAgency().getId()
                        : null,
                agencyOffice.getOfficeMail(),
                agencyOffice.getOfficeContactPersonName(),
                agencyOffice.getOfficeContactNumber(),
                agencyOffice.getAddress() != null
                        ? agencyOffice.getAddress().getId()
                        : null
        );
    }

    public static AgencyOffice toEntity(
            AgencyOfficeRequestDTO dto,
            Agency agency,
            Address address
    ) {

        if (dto == null) {
            return null;
        }

        AgencyOffice agencyOffice = new AgencyOffice();

        agencyOffice.setAgency(agency);
        agencyOffice.setOfficeMail(dto.getOfficeMail());
        agencyOffice.setOfficeContactPersonName(dto.getOfficeContactPersonName());
        agencyOffice.setOfficeContactNumber(dto.getOfficeContactNumber());
        agencyOffice.setAddress(address);

        return agencyOffice;
    }
}