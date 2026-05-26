package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * JPA entity representing the {@code agency_offices} table in the database.
 *
 * <p>An AgencyOffice is a physical regional branch office operated by an {@link Agency}.</p>
 */
@Entity
@Table(name = "agency_offices")
public class AgencyOffice {

    /** Auto-generated primary key for the agency office record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id")
    private Integer id;

    /** The parent agency that operates this branch office. */
    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    /** The contact email address of the branch office; required, must be valid syntax format. */
    @Email(message = "Office email must be valid")
    @Size(max = 100, message = "Office email must not exceed 100 characters")
    @Column(name = "office_mail", length = 100)
    private String officeMail;

    /** The name of the primary contact person for this branch office. */
    @Size(max = 50, message = "Office contact person name must not exceed 50 characters")
    @Column(name = "office_contact_person_name", length = 50)
    private String officeContactPersonName;

    /** The contact phone number of the branch office; required, must be exactly 10 digits. */
    @Pattern(regexp = "^\\d{10}$", message = "Office contact number must contain exactly 10 digits")
    @Size(max = 10, message = "Office contact number must not exceed 10 characters")
    @Column(name = "office_contact_number", columnDefinition = "CHAR(10)", length = 10)
    private String officeContactNumber;

    /** The physical address location of this branch office. */
    @ManyToOne
    @JoinColumn(name = "office_address_id")
    private Address address;

    /**
     * Default no-argument constructor required by Hibernate/JPA.
     */
    public AgencyOffice() {
    }

    /**
     * Parameterized constructor to fully initialize an AgencyOffice.
     *
     * @param id                      the office ID
     * @param agency                  the parent transport agency
     * @param officeMail              the contact email of the office
     * @param officeContactPersonName the contact person's name
     * @param officeContactNumber    the office contact phone number
     * @param address                 the physical address of the office
     */
    public AgencyOffice(Integer id, Agency agency, String officeMail, String officeContactPersonName, String officeContactNumber, Address address) {
        this.id = id;
        this.agency = agency;
        this.officeMail = officeMail;
        this.officeContactPersonName = officeContactPersonName;
        this.officeContactNumber = officeContactNumber;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public String getOfficeMail() {
        return officeMail;
    }

    public void setOfficeMail(String officeMail) {
        this.officeMail = officeMail;
    }

    public String getOfficeContactPersonName() {
        return officeContactPersonName;
    }

    public void setOfficeContactPersonName(String officeContactPersonName) {
        this.officeContactPersonName = officeContactPersonName;
    }

    public String getOfficeContactNumber() {
        return officeContactNumber;
    }

    public void setOfficeContactNumber(String officeContactNumber) {
        this.officeContactNumber = officeContactNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgencyOffice that = (AgencyOffice) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "AgencyOffice{" +
                "id=" + id +
                ", agencyId=" + (agency != null ? agency.getId() : null) +
                ", officeMail='" + officeMail + '\'' +
                ", officeContactPersonName='" + officeContactPersonName + '\'' +
                ", officeContactNumber='" + officeContactNumber + '\'' +
                ", addressId=" + (address != null ? address.getId() : null) +
                '}';
    }
}