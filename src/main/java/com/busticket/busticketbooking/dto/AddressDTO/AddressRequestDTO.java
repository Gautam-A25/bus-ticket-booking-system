package com.busticket.busticketbooking.dto.AddressDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing a request to create or update an Address.
 * Contains validation rules for incoming address data.
 */
public class AddressRequestDTO {

    /** The street address details; required, max 255 characters. */
    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    /** The city name; required, max 255 characters. */
    @NotBlank(message = "City is required")
    @Size(max = 255, message = "City must not exceed 255 characters")
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z\\s.'-]*$",
            message = "Must contain only letters"
    )
    private String city;

    /** The state name; required, max 255 characters. */
    @NotBlank(message = "State is required")
    @Size(max = 255, message = "State must not exceed 255 characters")
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z\\s.'-]*$",
            message = "Must contain only letters"
    )
    private String state;

    /** The postal zip code; required, must match alphanumeric/space/hyphen pattern, length 3 to 10. */
    @NotBlank(message = "Zip code is required")
    @Size(max = 10, message = "Zip code must not exceed 10 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9\\- ]{3,10}$",
            message = "Zip code format is invalid"
    )
    private String zipCode;

    /**
     * Default no-argument constructor.
     */
    public AddressRequestDTO() {
    }

    /**
     * Parameterized constructor to fully initialize the request DTO.
     *
     * @param address the street address details
     * @param city    the city name
     * @param state   the state name
     * @param zipCode the postal zip code
     */
    public AddressRequestDTO(String address, String city, String state, String zipCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
