package com.rental.setu.controllers;

import com.rental.setu.model.Cars;
import com.rental.setu.model.RentalRequestDTO;
import com.rental.setu.model.Rentals;
import com.rental.setu.services.ITripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

/**
 * Controller class exposing three endpoints
 */
@CrossOrigin
@RestController
@RequestMapping("/v1/trips")
@Tag(name = "TripController", description = "APIs related to searching, booking and viewing trips")
public class TripController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    /**
     * Autowired service bean injected during run-time
     */
    @Autowired
    ITripService tripService;

    /**
     * GET endpoint to view all trips booked by the user
     * with pagination
     *
     * @param userId Identifies the user
     * @param size Number of records per page
     * @param pageNo Page number
     * @return List of all trips booked by that user
     * @throws Exception
     */
    @GetMapping("/{user_id}")
    @Operation(summary = "getTripsByUserId", description = "GET all trips made by the specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Rentals.class))}),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<Object> getTripsByUserId(@PathVariable("user_id") Integer userId,
                                                   @RequestParam(value = "size") int size,
                                                   @RequestParam(value = "pageNo") int pageNo) throws Exception {
        LOGGER.info("------------getTripsByUserId(): for user_id = " + userId + ", page " + pageNo + " of size "
                + size + "------------");
        List<Rentals> trips = tripService.viewAllTrips(userId, size, pageNo);
        LOGGER.info("------------getTripsByUserId(): found " + trips.size() + " trips------------");
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    /**
     * GET all cars available for a given trip request
     *
     * @param type One of the three - MVP, SUV, SEDAN
     * @param crz availability zone
     * @param from_date start date for the trip
     * @param to_date end date for the trip
     * @return list of available cars on rent for given filters
     * @throws Exception
     */
    @GetMapping("/search")
    @Operation(summary = "searchForCars", description = "GET all cars of specified type available for rent for " +
            "specified duration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cars.class))}),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<Object> searchForCars(@RequestParam(value = "type") String type,
                                                @RequestParam(value = "availability_zone") String crz,
                                                @RequestParam(value = "from_date") String from_date,
                                                @RequestParam(value = "to_date") String to_date) throws Exception {
        LOGGER.info("------------searchForCars(): filters[type=" + type + ", crz=" + crz + ", from_date=" + from_date + ", to_date=" + to_date + "]");
        List<Cars> cars = tripService.searchForCars(type, crz, Date.valueOf(from_date), Date.valueOf(to_date));
        LOGGER.info("------------searchForCars(): found " + cars.size() + " cars available on rent for given trip request");
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    /**
     * POST endpoint to validate and save new trip reservation
     *
     * @param rentalRequestDTO reservation request made by a customer for a car between start date and end date
     * @return saved reservation details
     * @throws Exception
     */
    @PostMapping("/reserve")
    @Operation(summary = "reserveTrip", description = "POST a new reservation for a car between a specified duration to be saved")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Details stored successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Rentals.class))}),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<Object> reserveTrip(@RequestBody @Valid RentalRequestDTO rentalRequestDTO) throws Exception {
        LOGGER.info("------------reserveTrip(): for customer_id = " + rentalRequestDTO.getCustomer_id() + ", car_id = "
                + rentalRequestDTO.getCar_id() + "------------");
        Rentals savedReservation = tripService.reserveTrip(rentalRequestDTO);
        return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
    }

}
