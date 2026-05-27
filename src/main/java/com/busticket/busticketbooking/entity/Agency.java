package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * JPA entity representing the {@code agencies} table in the database.
 *
 * <p>An Agency operates bus transport services, owning one or more {@link AgencyOffice} physical branches.</p>
 */
@Entity
@Table(name = "agencies")
public class Agency {

    /** Auto-generated primary key for the agency record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_id")
    private Integer id;

    /** The official name of the transport agency; required, max 255 characters. */
    @NotBlank(message = "Agency name is required")
    @Size(max = 255, message = "Agency name must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    private String name;

    /** The name of the primary contact person for the agency; required, max 30 characters. */
    @NotBlank(message = "Contact person name is required")
    @Size(max = 30, message = "Contact person name must not exceed 30 characters")
    @Column(name = "contact_person_name", nullable = false, length = 30)
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z\\s.'-]*$",
            message = "Must contain only letters and valid name characters"
    )
    private String contactPersonName;

    /** Primary contact email address; required, must be valid syntax format. */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    private String email;

    /** Primary contact phone number; required, allows 10 to 15 digits. */
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must contain 10 to 15 digits")
    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    @Column(nullable = false, length = 15)
    private String phone;

    /**
     * Default no-argument constructor required by Hibernate/JPA.
     */
    public Agency() {
    }

    /**
     * Parameterized constructor to fully initialize an Agency.
     *
     * @param id                the agency ID
     * @param name              the official name of the transport agency
     * @param contactPersonName the primary contact person's name
     * @param email             the primary contact email
     * @param phone             the primary contact phone number
     */
    public Agency(Integer id, String name, String contactPersonName, String email, String phone) {
        this.id = id;
        this.name = name;
        this.contactPersonName = contactPersonName;
        this.email = email;
        this.phone = phone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agency agency = (Agency) o;
        return id != null && id.equals(agency.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Agency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactPersonName='" + contactPersonName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}