package com.busticket.busticketbooking.dto.DriverDTO;

import java.lang.Integer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
 * DTO = Data Transfer Object
 *
 * This class is used to receive request data
 * from client/API while creating or updating Driver.
 *
 * Request DTO helps separate API layer
 * from Entity/database layer.
 */
public class DriverRequestDTO {

    /*
     * Stores office ID associated with driver.
     *
     * @NotNull ensures office ID is mandatory.
     */
    @NotNull(message = "Office ID is required")
    private Integer officeId;

    /*
     * Stores address ID of driver.
     *
     * This field is optional,
     * so no validation is added.
     */
    private Integer addressId;

    /*
     * Stores driver's license number.
     *
     * @NotBlank ensures value is not null or empty.
     *
     * @Size limits maximum length to 20 characters.
     *
     * @Pattern validates license number format.
     */
    @NotBlank(message = "License number is required")
    @Size(
            max = 20,
            message = "License number must not exceed 20 characters"
    )
    @Pattern(
            regexp = "^[A-Za-z0-9\\- ]{3,20}$",
            message = "License number format is invalid"
    )
    private String licenseNumber;

    /*
     * Stores driver name.
     *
     * @NotBlank ensures value is mandatory.
     *
     * @Size restricts maximum length.
     */
    @NotBlank(message = "Driver name is required")
    @Size(
            max = 255,
            message = "Driver name must not exceed 255 characters"
    )
    private String name;

    /*
     * Stores driver phone number.
     *
     * @Pattern ensures only digits are allowed
     * and number length should be between 10 to 15.
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    @Size(
            max = 15,
            message = "Phone number must not exceed 15 characters"
    )
    private String phone;

    /*
     * Default constructor.
     *
     * Required for object creation by Spring/Jackson.
     */
    public DriverRequestDTO() {
    }

    /*
     * Returns office ID.
     */
    public Integer getOfficeId() {
        return officeId;
    }

    /*
     * Sets office ID.
     */
    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    /*
     * Returns address ID.
     */
    public Integer getAddressId() {
        return addressId;
    }

    /*
     * Sets address ID.
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /*
     * Returns license number.
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /*
     * Sets license number.
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /*
     * Returns driver name.
     */
    public String getName() {
        return name;
    }

    /*
     * Sets driver name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Returns phone number.
     */
    public String getPhone() {
        return phone;
    }

    /*
     * Sets phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}