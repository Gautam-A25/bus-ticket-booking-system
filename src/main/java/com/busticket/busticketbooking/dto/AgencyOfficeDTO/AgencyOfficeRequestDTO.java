package com.busticket.busticketbooking.dto.AgencyOfficeDTO;

import jakarta.validation.constraints.*;


public class AgencyOfficeRequestDTO {

    @NotBlank(message = "Office email is required")
    @Email(message = "Office email format is invalid")
    @Size(max = 100, message = "Office email must not exceed 100 characters")
    private String officeMail;

    @NotBlank(message = "Office contact person name is required")
    @Size(max = 50, message = "Office contact person name must not exceed 50 characters")
    private String officeContactPersonName;

    @NotBlank(message = "Office contact number is required")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Office contact number must contain exactly 10 digits"
    )
    @Size(max = 10, message = "Office contact number must not exceed 10 characters")
    private String officeContactNumber;

    @NotNull(message = "Address ID is required")
    private Integer addressId;

    public AgencyOfficeRequestDTO() {
    }

    public AgencyOfficeRequestDTO(String officeMail, String officeContactPersonName, String officeContactNumber, Integer addressId) {
        this.officeMail = officeMail;
        this.officeContactPersonName = officeContactPersonName;
        this.officeContactNumber = officeContactNumber;
        this.addressId = addressId;
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