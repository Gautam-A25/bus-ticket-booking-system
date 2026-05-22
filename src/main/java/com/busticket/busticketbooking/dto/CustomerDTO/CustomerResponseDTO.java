package com.busticket.busticketbooking.dto.CustomerDTO;

// DTO used to send customer details as response
public class CustomerResponseDTO {

    // Stores unique customer ID
    private Integer id;

    // Stores customer name
    private String name;

    // Stores customer email address
    private String email;

    // Stores customer phone number
    private String phone;

    // Stores address ID linked to customer
    private Integer addressId;

    // Default constructor
    public CustomerResponseDTO() {
    }

    // Parameterized constructor for object initialization
    public CustomerResponseDTO(
            Integer id,
            String name,
            String email,
            String phone,
            Integer addressId
    ) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.addressId = addressId;
    }

    // Returns customer ID
    public Integer getId() {
        return id;
    }

    // Sets customer ID
    public void setId(Integer id) {
        this.id = id;
    }

    // Returns customer name
    public String getName() {
        return name;
    }

    // Sets customer name
    public void setName(String name) {
        this.name = name;
    }

    // Returns customer email
    public String getEmail() {
        return email;
    }

    // Sets customer email
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns customer phone number
    public String getPhone() {
        return phone;
    }

    // Sets customer phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Returns address ID
    public Integer getAddressId() {
        return addressId;
    }

    // Sets address ID
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}