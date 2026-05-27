package com.busticket.busticketbooking.dto.AgencyDTO;

/**
 * Data Transfer Object representing a response for an Agency.
 * Used to send detailed agency information back to the client.
 */
public class AgencyResponseDTO {

    /** The unique identifier of the agency. */
    private Integer agencyId;

    /** The official name of the transport agency. */
    private String name;

    /** The name of the primary contact person. */
    private String contactPersonName;

    /** The contact email address. */
    private String email;

    /** The contact phone number. */
    private String phone;

    /**
     * Default no-argument constructor.
     */
    public AgencyResponseDTO() {
    }

    /**
     * Parameterized constructor to fully initialize the response DTO.
     *
     * @param agencyId          the unique ID of the agency
     * @param name              the official agency name
     * @param contactPersonName the contact person's name
     * @param email             the contact email
     * @param phone             the contact phone number
     */
    public AgencyResponseDTO(Integer agencyId, String name, String contactPersonName, String email, String phone) {
        this.agencyId = agencyId;
        this.name = name;
        this.contactPersonName = contactPersonName;
        this.email = email;
        this.phone = phone;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
