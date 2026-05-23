package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/*
 * @Entity tells Hibernate that this class is a database entity
 * and it should create/manage a table for this class.
 */
@Entity

/*
 * @Table is used to specify the table name in MySQL database.
 * Here the table name is "buses".
 */
@Table(name = "buses")
public class Bus {

    /*
     * @Id marks this field as Primary Key.
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * means ID will be auto-incremented by MySQL.
     *
     * @Column is used to specify database column name.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer id;

    /*
     * @NotNull validation ensures office value cannot be null.
     *
     * @ManyToOne defines relationship:
     * Many buses can belong to one office.
     *
     * @JoinColumn creates foreign key column "office_id".
     */
    @NotNull(message = "Office is required")
    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private AgencyOffice office;

    /*
     * @NotBlank ensures field is not null and not empty.
     *
     * @Size restricts maximum length.
     *
     * @Pattern validates registration number format.
     *
     * @Column defines DB column properties.
     */
    @NotBlank(message = "Registration number is required")
    @Size(max = 20, message = "Registration number must not exceed 20 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9\\- ]{3,20}$",
            message = "Registration number format is invalid"
    )
    @Column(name = "registration_number", nullable = false, length = 20)
    private String registrationNumber;

    /*
     * Capacity cannot be null.
     *
     * @Positive ensures value is greater than 0.
     */
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    @Column(nullable = false)
    private Integer capacity;

    /*
     * Type stores bus category like AC, Sleeper, Volvo etc.
     *
     * Validation ensures proper value length.
     */
    @NotBlank(message = "Bus type is required")
    @Size(max = 30, message = "Bus type must not exceed 30 characters")
    @Column(nullable = false, length = 30)
    private String type;

    /*
     * Default constructor required by Hibernate/JPA.
     */
    public Bus() {
    }

    /*
     * Parameterized constructor used to create object with values.
     */
    public Bus(Integer id,
               AgencyOffice office,
               String registrationNumber,
               Integer capacity,
               String type) {

        this.id = id;
        this.office = office;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.type = type;
    }

    /*
     * Getter method returns bus id.
     */
    public Integer getId() {
        return id;
    }

    /*
     * Setter method sets bus id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /*
     * Returns associated office object.
     */
    public AgencyOffice getOffice() {
        return office;
    }

    /*
     * Sets office object.
     */
    public void setOffice(AgencyOffice office) {
        this.office = office;
    }

    /*
     * Returns registration number.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /*
     * Sets registration number.
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /*
     * Returns seating capacity.
     */
    public Integer getCapacity() {
        return capacity;
    }

    /*
     * Sets seating capacity.
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /*
     * Returns bus type.
     */
    public String getType() {
        return type;
    }

    /*
     * Sets bus type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /*
     * equals() method is used to compare two Bus objects.
     *
     * Two objects are considered equal if their IDs are equal.
     */
    @Override
    public boolean equals(Object o) {

        // If both references point to same object
        if (this == o) return true;

        // If object is null or classes differ
        if (o == null || getClass() != o.getClass()) return false;

        // Type casting Object to Bus
        Bus bus = (Bus) o;

        // Compare IDs
        return id != null && id.equals(bus.id);
    }

    /*
     * hashCode() is used internally in collections like HashSet and HashMap.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /*
     * toString() returns object details in readable string format.
     * Useful for logging and debugging.
     */
    @Override
    public String toString() {

        return "Bus{" +
                "id=" + id +
                ", officeId=" + (office != null ? office.getId() : null) +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                ", type='" + type + '\'' +
                '}';
    }
}