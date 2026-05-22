package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepo extends JpaRepository<Driver, Integer> {

    List<Driver> findByOffice_Id(Integer officeId);

    boolean existsByLicenseNumber(String licenseNumber);

    List<Driver> findByAddressId(Integer addressId);
}