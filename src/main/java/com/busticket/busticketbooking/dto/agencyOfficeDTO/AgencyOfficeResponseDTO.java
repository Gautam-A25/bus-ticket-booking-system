package com.busticket.busticketbooking.dto.agencyOfficeDTO;

public class AgencyOfficeResponseDTO {

    private Integer officeId;
    private Integer agencyId;
    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;
    private Integer addressId;

    public AgencyOfficeResponseDTO() {
    }

    public AgencyOfficeResponseDTO(Integer officeId, Integer agencyId, String officeMail,
                                   String officeContactPersonName, String officeContactNumber, Integer addressId) {
        this.officeId = officeId;
        this.agencyId = agencyId;
        this.officeMail = officeMail;
        this.officeContactPersonName = officeContactPersonName;
        this.officeContactNumber = officeContactNumber;
        this.addressId = addressId;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getOfficeMail() {
        return officeMail;
    }

    public void setOfficeMail(String officeMail) {
        this.officeMail = officeMail;
    }

    public String getOfficeContactPersonName() {
        return officeContactPersonName;
    }

    public void setOfficeContactPersonName(String officeContactPersonName) {
        this.officeContactPersonName = officeContactPersonName;
    }

    public String getOfficeContactNumber() {
        return officeContactNumber;
    }

    public void setOfficeContactNumber(String officeContactNumber) {
        this.officeContactNumber = officeContactNumber;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
