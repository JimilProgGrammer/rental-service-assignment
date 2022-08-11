package com.rental.setu.services;

import com.rental.setu.model.Cars;
import com.rental.setu.model.RentalRequestDTO;
import com.rental.setu.model.Rentals;
import com.rental.setu.model.Users;
import com.rental.setu.repositories.CarsRepositoryImpl;
import com.rental.setu.repositories.RentalsRepositoryImpl;
import com.rental.setu.repositories.UsersRepositoryImpl;
import com.rental.setu.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

/**
 * Service class that cleans all incoming requests
 * for trip information and calls corresponding
 * CrudRepository method.
 *
 * @author jimil
 */
@Service
public class TripServiceImpl implements ITripService{

    private static final Logger LOG = LoggerFactory.getLogger(TripServiceImpl.class);
    private static final ArrayList<String> validCarTypes = new ArrayList<>(Arrays.asList("SUV","MVP","SEDAN"));

    @Autowired
    private AppUtils appUtils;

    /**
     * Autowired repository bean injected during run-time
     */
    @Autowired
    private RentalsRepositoryImpl rentalsRepository;

    @Autowired
    private CarsRepositoryImpl carsRepository;

    @Autowired
    private UsersRepositoryImpl usersRepository;

    /**
     * Given a user_id and a page configuration, get all trips
     * booked by that user_id
     *
     * @param userId Identifies the user
     * @param size Number of records per page
     * @param pageNo Page number
     * @return List of all rental trips booked by the user
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rentals> viewAllTrips(Integer userId, int size, int pageNo) throws Exception {
        if(userId <= 0) {
            throw new Exception("Invalid user_id supplied");
        }
        if(size <= 0) {
            throw new Exception("Unsupported size");
        }
        Pageable page = PageRequest.of(pageNo, size);
        return rentalsRepository.findAllByUser(userId, page);
    }

    /**
     * Search for cars that can be booked given trip details
     *
     * @param type type of the car - either SUV, MVP, or SEDAN
     * @param crz availability zone being searched
     * @param fromDate starting date of the trip
     * @param toDate ending date of the trip
     * @return list of available cars along with their owner details
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cars> searchForCars(String type, String crz, Date fromDate, Date toDate) throws Exception {
        if(!fromDate.before(toDate)) {
            throw new Exception("from_date cannot be after the to_date");
        }
        if(appUtils.getIntervalInDaysFromNow(fromDate) > 90L || appUtils.getIntervalInDaysFromNow(toDate) > 90L) {
            throw new Exception("Advance booking is allowed for only up to 90 days");
        }
        if(!validCarTypes.contains(type)) {
            throw new Exception("Invalid car type");
        }

        Set<Integer> carsMatchingTypeAndCrz = new HashSet<>(carsRepository.findAllByCrzAndType(crz, type));
        if(carsMatchingTypeAndCrz.size() == 0) {
            return Collections.emptyList();
        }
        LOG.info("Cars that match type and crz: " + carsMatchingTypeAndCrz.toString());
        Set<Integer> carsBookedBetweenSameDates = new HashSet<>(rentalsRepository.findRentalsBetweenFromDateAndToDate
                (fromDate, toDate, type, crz));
        if(carsBookedBetweenSameDates.size() == 0) {
           return carsRepository.findAllByCar_ids(carsMatchingTypeAndCrz);
        }
        LOG.info("Cars that match filter and are booked between the same dates: " + carsBookedBetweenSameDates.toString());
        Set<Integer> availableCars = appUtils.difference(carsMatchingTypeAndCrz, carsBookedBetweenSameDates);
        LOG.info("Cars available for rent: " + availableCars.toString());
        if(availableCars.size() > 0) {
            return carsRepository.findAllByCar_ids(availableCars);
        }
        return Collections.emptyList();
    }

    /**
     * Takes in a trip reservation request and saves it
     * after validating
     *
     * For a reservation request to be valid, following conditions
     * should be met:
     * 1. From date should be before to date
     * 2. From date and to date both can be upto 90 days from now, no more
     * 3. car_id given in the request must exist in the database
     * 4. user_id given in the request must exist in the database
     *
     * @param rentalRequestDTO trip reservation request
     * @return saved entity in the database
     * @throws Exception
     */
    @Override
    public Rentals reserveTrip(RentalRequestDTO rentalRequestDTO) throws Exception {
        Date fromDate = rentalRequestDTO.getFrom_date();
        Date toDate = rentalRequestDTO.getTo_date();
        if(!fromDate.before(toDate)) {
            throw new Exception("from_date cannot be after the to_date");
        }
        if(appUtils.getIntervalInDaysFromNow(fromDate) > 90L || appUtils.getIntervalInDaysFromNow(toDate) > 90L) {
            throw new Exception("Advance booking is allowed for only up to 90 days");
        }
        Cars car = carsRepository.findByCar_id(rentalRequestDTO.getCar_id());
        if(car == null) {
            throw new Exception("Invalid car details");
        }
        Users user = usersRepository.findByUser_id(rentalRequestDTO.getCustomer_id());
        if(user == null) {
            throw new Exception("Invalid customer details");
        }
        LOG.info("Found for car_id = " + rentalRequestDTO.getCar_id() + " -> " + car);
        LOG.info("Found for customer_id = " + rentalRequestDTO.getCustomer_id() + " -> " + user);

        float amount = this.getAmountForTrip(car, rentalRequestDTO.getFrom_date(), rentalRequestDTO.getTo_date());
        LOG.info("Calculated amount for trip = " + amount);
        Integer savedRentalId = rentalsRepository.save(amount, rentalRequestDTO.getFrom_date(), rentalRequestDTO.getTo_date(),
                rentalRequestDTO.getLocation(), user.getUser_id(), car.getCar_id());
        LOG.info("Saved rental record id = " + savedRentalId);
        return rentalsRepository.findById(savedRentalId).orElseThrow(RuntimeException::new);
    }

    /**
     * Given a car object identifying its type, and the trip
     * range (from_date - to_date), calculate and return
     * price for the entire trip
     *
     * @param car Car entity
     * @param fromDate starting date of the trip
     * @param toDate ending date of the trip
     * @return amount for trip
     */
    private float getAmountForTrip(Cars car, Date fromDate, Date toDate) {
        float amount = 0.0f;
        int tripDuration = (int) appUtils.getIntervalInDays(fromDate, toDate);
        switch(car.getType().toUpperCase()) {
            case "SUV": amount = 2000 * tripDuration;
                break;
            case "MVP": amount = 1500 * tripDuration;
                break;
            case "SEDAN": amount = 1800 * tripDuration;
                break;
        }
        return amount;
    }

}
