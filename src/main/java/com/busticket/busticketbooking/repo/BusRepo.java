package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepo extends JpaRepository<Bus, Integer> {

    List<Bus> findByOffice_id(Integer officeId);

}