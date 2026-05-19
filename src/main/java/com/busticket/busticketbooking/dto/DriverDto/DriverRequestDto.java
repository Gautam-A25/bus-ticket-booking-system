package com.busticket.busticketbooking.dto.DriverDTO;

import java.lang.Integer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DriverRequestDto {

    @NotNull(message = "Office ID is required")
    private Integer officeId;

    private Integer addressId;

    @NotBlank(message = "License number is required")
    @Size(max = 20, message = "License number must not exceed 20 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9\\- ]{3,20}$",
            message = "License number format is invalid"
    )
    private String licenseNumber;

    @NotBlank(message = "Driver name is required")
    @Size(max = 255, message = "Driver name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    private String phone;

    public DriverRequestDto() {
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}