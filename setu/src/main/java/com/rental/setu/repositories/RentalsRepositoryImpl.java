package com.rental.setu.repositories;

import com.rental.setu.model.Rentals;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.sql.Date;
import java.util.List;

/**
 * Extend CrudRepository over Rentals Entity to
 * perform SQL operations on the car_rentals table
 *
 * @author jimil
 */
@Repository
public interface RentalsRepositoryImpl extends PagingAndSortingRepository<Rentals, Integer> {

    /**
     * Find all trips that have been booked by the user
     *
     * @param user_id identifies the customer
     * @param pageable page config - size and number
     * @return paginated list of all rental trips booked by the user
     */
    @Query("SELECT c FROM Rentals c WHERE c.customer.user_id=?1")
    public List<Rentals> findAllByUser(Integer user_id, Pageable pageable);

    /**
     * Search for cars matching type and crz and that are booked
     * for rental trips between the same dates
     *
     * Employs an optimistic locking when searching in the rentals
     * table so as to enable thread-safe searching
     *
     * @param fromDate start date for the trip
     * @param toDate end date for the trip
     * @param type type of car to be booked
     * @param crz availability zone
     * @return list of cars ids matching criteria
     */
    @Query("SELECT c.car_booked.car_id FROM Rentals c WHERE ((c.from_date >= ?1 AND c.to_date <= ?2) " +
            "OR (c.from_date <= ?1 AND c.to_date >= ?2)) " +
            "AND c.car_booked.type = ?3 AND c.car_booked.crz = ?4")
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public List<Integer> findRentalsBetweenFromDateAndToDate(Date fromDate, Date toDate, String type, String crz);

    /**
     * Book a new reservation successfully by inserting details
     * into the car_rentals table
     *
     * @param amount amount for the trip
     * @param from_date start date
     * @param to_date end date
     * @param location pickup and drop location for the car
     * @param customer_id user booking the trip
     * @param car_id car reserved for the trip
     * @return id generated for the new car_rentals record
     */
    @Modifying
    @Query(value = "INSERT INTO car_rentals (amount, from_date, to_date, location, user_id, car_id) VALUES(?1,?2,?3,?4,?5,?6)", nativeQuery = true)
    @Transactional
    public Integer save(float amount, Date from_date, Date to_date, String location, Integer customer_id, Integer car_id);

}
