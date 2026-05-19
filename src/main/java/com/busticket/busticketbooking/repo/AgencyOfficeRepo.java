package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.AgencyOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyOfficeRepo extends JpaRepository<AgencyOffice, Integer> {
    List<AgencyOffice> findByAgency_Id(Integer agencyId);
}