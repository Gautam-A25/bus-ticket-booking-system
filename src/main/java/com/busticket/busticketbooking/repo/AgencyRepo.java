package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepo extends JpaRepository<Agency, Integer> {
}