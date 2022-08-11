package com.rental.setu.services;

import com.rental.setu.model.Rentals;
import com.rental.setu.repositories.CarsRepositoryImpl;
import com.rental.setu.repositories.RentalsRepositoryImpl;
import com.rental.setu.repositories.UsersRepositoryImpl;
import com.rental.setu.utils.AppUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TripServiceImplTests {

    @Mock
    private AppUtils appUtils;

    @Mock
    private RentalsRepositoryImpl rentalsRepository;

    @Mock
    private CarsRepositoryImpl carsRepository;

    @Mock
    private UsersRepositoryImpl usersRepository;

    @InjectMocks
    TripServiceImpl tripService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Viewing all trips returns paged data successfully")
    @Test
    public void whenViewingAllTripsReturnsPagedDataSuccessfully() throws Exception {
        // setup test data
        Integer userId = 100;
        int size = 10;
        int pageNo = 0;
        // Stub
        Mockito.when(rentalsRepository.findAllByUser(Mockito.eq(userId), Mockito.any(Pageable.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(new Rentals())));
        // execute
        List<Rentals> rentals = tripService.viewAllTrips(userId, size, pageNo);
        // verify
        Assertions.assertNotNull(rentals);
        Assertions.assertEquals(1, rentals.size());
    }

    @DisplayName("Viewing all trips with invalid user id throws Exception")
    @Test
    public void whenViewingAllTripsWithInvalidUserIdThenThrowsException() throws Exception {
        // setup test data
        Integer userId = 0;
        int size = 10;
        int pageNo = 0;

        // verify
        Assertions.assertThrows(Exception.class, () -> {
            tripService.viewAllTrips(userId, size, pageNo);
        });
    }

    @DisplayName("Viewing all trips returns with invalid page size throws Exception")
    @Test
    public void whenViewingAllTripsWithInvalidPageSizeThenThrowsException() throws Exception {
        // setup test data
        Integer userId = 100;
        int size = 0;
        int pageNo = 0;

        // verify
        Assertions.assertThrows(Exception.class, () -> {
            tripService.viewAllTrips(userId, size, pageNo);
        });
    }

}
