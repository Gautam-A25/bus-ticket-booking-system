package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepo extends JpaRepository<Trip, Integer> {
}