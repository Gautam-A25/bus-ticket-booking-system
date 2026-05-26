package com.busticket.busticketbooking.dto.AgencyDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing a request to create or update an Agency.
 * Contains validation rules for incoming agency details.
 */
public class AgencyRequestDTO {

    /** The official name of the transport agency; required, max 255 characters. */
    @NotBlank(message = "Agency name is required")
    @Size(max = 255, message = "Agency name must not exceed 255 characters")
    private String name;

    /** The name of the primary contact person; required, max 30 characters. */
    @NotBlank(message = "Contact person name is required")
    @Size(max = 30, message = "Contact person name must not exceed 30 characters")
    private String contactPersonName;

    /** The contact email address; required, must be valid email format, max 255 characters. */
    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    /** The contact phone number; required, 10 to 15 digits, max 15 characters. */
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    private String phone;

    /**
     * Default no-argument constructor.
     */
    public AgencyRequestDTO() {
    }

    /**
     * Parameterized constructor to fully initialize the request DTO.
     *
     * @param name              the official agency name
     * @param contactPersonName the contact person's name
     * @param email             the contact email
     * @param phone             the contact phone number
     */
    public AgencyRequestDTO(String name, String contactPersonName, String email, String phone) {
        this.name = name;
        this.contactPersonName = contactPersonName;
        this.email = email;
        this.phone = phone;
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


