package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Repository layer is responsible for
 * interacting with the database.
 *
 * JpaRepository provides built-in CRUD methods like:
 *
 * save()
 * findById()
 * findAll()
 * deleteById()
 * existsById()
 *
 * So we do not need to write SQL queries manually.
 */

/*
 * DriverRepo manages Driver entity operations.
 *
 * <Driver, Integer>
 *
 * Driver   -> Entity class
 * Integer  -> Primary key datatype
 */
public interface DriverRepo extends JpaRepository<Driver, Integer> {

    /*
     * Custom finder method.
     *
     * Spring Data JPA automatically creates query
     * from method name.
     *
     * This method fetches all drivers
     * belonging to a specific office.
     *
     * Equivalent SQL:
     *
     * SELECT * FROM drivers WHERE office_id = ?
     */
    List<Driver> findByOffice_id(Integer officeId);

    /*
     * Checks whether a driver already exists
     * with the given license number.
     *
     * Returns:
     * true  -> if license number already exists
     * false -> if not exists
     *
     * Equivalent SQL:
     *
     * SELECT COUNT(*) > 0
     * FROM drivers
     * WHERE license_number = ?
     */
    boolean existsByLicenseNumber(String licenseNumber);
}