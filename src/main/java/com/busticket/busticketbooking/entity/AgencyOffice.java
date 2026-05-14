package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agency_offices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @Column(name = "office_mail", length = 100)
    private String officeMail;

    @Column(name = "office_contact_person_name", length = 50)
    private String officeContactPersonName;

    @Column(name = "office_contact_number", length = 10)
    private String officeContactNumber;

    @OneToOne
    @JoinColumn(name = "office_address_id")
    private Address address;
}
