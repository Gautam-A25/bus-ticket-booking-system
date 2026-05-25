package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Repository layer is used to interact with database.
 *
 * JpaRepository provides built-in CRUD operations like:
 *
 * save()
 * findById()
 * findAll()
 * deleteById()
 * existsById()
 *
 * We do not need to write SQL queries manually.
 */

/*
 * BusRepo manages Bus entity.
 *
 * <Bus, Integer>
 *
 * Bus      -> Entity class
 * Integer  -> Primary key datatype
 */
public interface BusRepo extends JpaRepository<Bus, Integer> {

    /*
     * Custom finder method.
     *
     * Spring Data JPA automatically creates query
     * based on method name.
     *
     * This method fetches all buses belonging
     * to a particular office ID.
     *
     * Equivalent SQL:
     *
     * SELECT * FROM buses WHERE office_id = ?
     */
    List<Bus> findByOffice_Id(Integer officeId);

}