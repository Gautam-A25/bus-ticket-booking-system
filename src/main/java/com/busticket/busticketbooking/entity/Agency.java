package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "contact_person_name", nullable = false, length = 30)
    private String contactPersonName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;
}
