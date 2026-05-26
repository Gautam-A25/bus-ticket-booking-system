package com.busticket.busticketbooking.dto.AddressDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressRequestDTO {

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 255, message = "City must not exceed 255 characters")
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z\\s.'-]*$",
            message = "Must contain only letters"
    )
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 255, message = "State must not exceed 255 characters")
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z\\s.'-]*$",
            message = "Must contain only letters"
    )
    private String state;

    @NotBlank(message = "Zip code is required")
    @Size(max = 10, message = "Zip code must not exceed 10 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9\\- ]{3,10}$",
            message = "Zip code format is invalid"
    )
    private String zipCode;

    public AddressRequestDTO() {
    }

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
