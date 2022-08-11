package com.rental.setu.services;

import com.rental.setu.model.Cars;
import com.rental.setu.model.RentalRequestDTO;
import com.rental.setu.model.Rentals;

import java.sql.Date;
import java.util.List;

public interface ITripService {

    public List<Rentals> viewAllTrips(Integer userId, int size, int pageNo) throws Exception;

    public List<Cars> searchForCars(String type, String crz, Date fromDate, Date toDate) throws Exception;

    public Rentals reserveTrip(RentalRequestDTO rentalRequestDTO) throws Exception;

}
