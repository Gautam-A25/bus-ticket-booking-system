package com.busticket.busticketbooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerDTO {

    private Integer id;

    @NotBlank(message = "Customer name is required")
    private String name;

    @Email(message = "Customer email format is invalid")
    @NotBlank(message = "Customer email is required")
    private String email;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Customer phone number must contain exactly 10 digits"
    )
    private String phone;

    @NotNull(message = "Customer address ID is required")
    private Integer addressId;

    public CustomerDTO() {
    }

    public CustomerDTO(Integer id, String name, String email, String phone, Integer addressId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.addressId = addressId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}