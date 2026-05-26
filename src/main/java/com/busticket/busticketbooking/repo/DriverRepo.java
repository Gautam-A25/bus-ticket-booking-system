package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for {@link Driver} database operations.
 *
 * <p>
 * Handles data operations for bus drivers, including office assignment lookups
 * and
 * license number uniqueness validation checks.
 * </p>
 */
@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer> {

    /**
     * Finds all drivers belonging to a specific agency office.
     *
     * <p>
     * Equivalent SQL: {@code SELECT * FROM drivers WHERE office_id = ?}
     * </p>
     *
     * @param officeId ID of the agency office
     * @return a list of drivers assigned to the office
     */
    List<Driver> findByOffice_Id(Integer officeId);

    /**
     * Checks whether a driver already exists with the given license number.
     *
     * <p>
     * Equivalent SQL:
     * {@code SELECT COUNT(*) > 0 FROM drivers WHERE license_number = ?}
     * </p>
     *
     * @param licenseNumber the driver's license number
     * @return true if the license number exists in the system, false otherwise
     */
    boolean existsByLicenseNumber(String licenseNumber);

    /**
     * Finds all drivers associated with a specific address ID.
     *
     * @param addressId ID of the address record
     * @return a list of drivers living at this address
     */
    List<Driver> findByAddressId(Integer addressId);
}