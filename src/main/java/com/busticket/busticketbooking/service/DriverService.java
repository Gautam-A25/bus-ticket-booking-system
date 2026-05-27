package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining all driver management operations.
 *
 * <p>A {@code Driver} belongs to an {@code AgencyOffice} and can optionally have
 * an associated {@code Address}. A driver may be assigned as driver1 or driver2
 * on a {@code Trip}. Deleting a driver cascades through any trips they are
 * assigned to (and those trips' bookings, payments, and reviews).</p>
 *
 * <p>Driver license numbers must be unique across the system.</p>
 */
public interface DriverService {

    /** Creates a new driver; throws {@link com.busticket.busticketbooking.exception.DuplicateResourceException} if the license number already exists. */
    DriverResponseDTO createDriver(DriverRequestDTO dto);

    /** Returns all drivers in the system (no pagination). */
    List<DriverResponseDTO> getAllDrivers();

    /** Returns a paginated slice of all drivers. */
    Page<DriverResponseDTO> getDriverPage(
            int page,
            int size
    );

    /** Returns the driver with the given ID; throws 404 if not found. */
    DriverResponseDTO getDriverById(Integer id);

    /** Returns all drivers assigned to the specified office. */
    List<DriverResponseDTO> getDriversByOffice(Integer officeId);

    /** Updates all fields of an existing driver; throws 404 if not found. */
    DriverResponseDTO updateDriver(
            Integer driverId,
            DriverRequestDTO dto
    );

    /**
     * Deletes a driver and cascades through any trips the driver was assigned to
     * (along with those trips' bookings, payments, and reviews).
     *
     * @return a summary message string
     */
    String deleteDriver(Integer id);
}
