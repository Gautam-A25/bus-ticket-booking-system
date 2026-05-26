package com.busticket.busticketbooking.dto.AgencyOfficeDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing a request to create or update an Agency Office.
 * Contains validation rules for incoming branch office details.
 */
public class AgencyOfficeRequestDTO {

    /** The identifier of the parent agency. */
    private Integer agencyId;

    /** The contact email address of the branch office; required, must be valid email format, max 100 characters. */
    @NotBlank(message = "Office email is required")
    @Email(message = "Office email format is invalid")
    @Size(max = 100, message = "Office email must not exceed 100 characters")
    private String officeMail;

    /** The contact person's name for this branch; required, max 50 characters. */
    @NotBlank(message = "Office contact person name is required")
    @Size(max = 50, message = "Office contact person name must not exceed 50 characters")
    private String officeContactPersonName;

    /** The contact phone number of the branch office; required, must be exactly 10 digits. */
    @NotBlank(message = "Office contact number is required")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Office contact number must contain exactly 10 digits"
    )
    @Size(max = 10, message = "Office contact number must not exceed 10 characters")
    private String officeContactNumber;

    /** The identifier of the associated physical address; required. */
    @NotNull(message = "Address ID is required")
    private Integer addressId;

    /**
     * Default no-argument constructor.
     */
    public AgencyOfficeRequestDTO() {
    }

    /**
     * Parameterized constructor to fully initialize the request DTO.
     *
     * @param agencyId                the parent agency ID
     * @param officeMail              the office contact email
     * @param officeContactPersonName the contact person's name
     * @param officeContactNumber    the office contact number
     * @param addressId               the associated address ID
     */
    public AgencyOfficeRequestDTO(Integer agencyId, String officeMail, String officeContactPersonName, String officeContactNumber, Integer addressId) {
        this.agencyId = agencyId;
        this.officeMail = officeMail;
        this.officeContactPersonName = officeContactPersonName;
        this.officeContactNumber = officeContactNumber;
        this.addressId = addressId;
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