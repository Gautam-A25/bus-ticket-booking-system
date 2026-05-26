package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Entity class mapped to customers table
@Entity

// Specifies database table name
@Table(name = "customers")
public class Customer {

    // Primary key of customers table
    @Id

    // Auto-generates customer ID values
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    // Stores customer name
    @NotBlank(message = "Customer name is required")

    // Restricts maximum name length
    @Size(max = 255, message = "Customer name must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z\\s.'-]*$",
            message = "Must contain only letters and valid name characters"
    )
    private String name;

    // Stores customer email address
    @NotBlank(message = "Email is required")

    // Validates email format
    @Email(message = "Email must be valid")

    // Restricts maximum email length
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    private String email;

    // Stores customer phone number
    @NotBlank(message = "Phone number is required")

    // Allows only 10 to 15 digit numbers
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must contain 10 to 15 digits")

    // Restricts maximum phone number length
    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    @Column(nullable = false, length = 15)
    private String phone;

    // Many customers can belong to one address
    @ManyToOne

    // Foreign key column for address reference
    @JoinColumn(name = "address_id")
    private Address address;

    // Default constructor
    public Customer() {
    }

    // Parameterized constructor for object initialization
    public Customer(Integer id, String name, String email, String phone, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
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

    // Returns associated address object
    public Address getAddress() {
        return address;
    }

    // Sets associated address object
    public void setAddress(Address address) {
        this.address = address;
    }

    // Compares customer objects using customer ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id != null && id.equals(customer.id);
    }

    // Generates hash code for customer object
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Returns customer object details as String
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", addressId=" + (address != null ? address.getId() : null) +
                '}';
    }
}