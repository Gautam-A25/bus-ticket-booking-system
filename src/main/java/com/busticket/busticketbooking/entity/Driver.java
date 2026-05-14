package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Integer id;

    @Column(name = "license_number", nullable = false, length = 20)
    private String licenseNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 15)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "office_id")
    private AgencyOffice office;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
