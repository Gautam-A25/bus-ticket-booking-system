package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.AgencyOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyOfficeRepo extends JpaRepository<AgencyOffice, Integer> {
}