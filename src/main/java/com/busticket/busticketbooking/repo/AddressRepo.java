package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
}