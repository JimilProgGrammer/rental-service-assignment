package com.rental.setu.repositories;

import com.rental.setu.model.Cars;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Extend CrudRepository over Cars Entity to
 * perform SQL operations on the cars table
 *
 * @author jimil
 */
public interface CarsRepositoryImpl extends CrudRepository<Cars, Integer> {

    /**
     * Find a single car info given car_id
     *
     * @param car_id unique identifier to records in the cars table
     * @return car record matching car_id, null otherwise
     */
    @Query("SELECT c from Cars c WHERE c.car_id=?1")
    public Cars findByCar_id(Integer car_id);

    /**
     * Find all records from cars table given a
     * list of car_ids
     *
     * @param carIds list of ids to match against
     * @return list of car records matching given ids; empty list otherwise
     */
    @Query("SELECT c from Cars c WHERE c.car_id IN ?1")
    public List<Cars> findAllByCar_ids(Iterable<Integer> carIds);

    /**
     * Search for records in cars table given crz and type
     *
     * @param crz availability zone to search for cars
     * @param type one of the three - MVP, SUV, SEDAN
     * @return list of cars ids matching given crz and type
     */
    @Query("SELECT c.car_id from Cars c WHERE c.crz=?1 AND c.type=?2")
    public List<Integer> findAllByCrzAndType(String crz, String type);

}
