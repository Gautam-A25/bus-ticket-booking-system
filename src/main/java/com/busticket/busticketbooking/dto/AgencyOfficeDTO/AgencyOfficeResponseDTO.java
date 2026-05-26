package com.busticket.busticketbooking.dto.AgencyOfficeDTO;

/**
 * Data Transfer Object representing a response for an Agency Office.
 * Used to send detailed branch office information back to the client.
 */
public class AgencyOfficeResponseDTO {

    /** The unique identifier of the agency office. */
    private Integer officeId;

    /** The identifier of the parent agency. */
    private Integer agencyId;

    /** The contact email address of the branch office. */
    private String officeMail;

    /** The contact person's name for this branch. */
    private String officeContactPersonName;

    /** The contact phone number of the branch office. */
    private String officeContactNumber;

    /** The identifier of the associated physical address. */
    private Integer addressId;

    /**
     * Default no-argument constructor.
     */
    public AgencyOfficeResponseDTO() {
    }

    /**
     * Parameterized constructor to fully initialize the response DTO.
     *
     * @param officeId                the unique ID of the agency office
     * @param agencyId                the parent agency ID
     * @param officeMail              the office contact email
     * @param officeContactPersonName the contact person's name
     * @param officeContactNumber    the office contact number
     * @param addressId               the associated address ID
     */
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
