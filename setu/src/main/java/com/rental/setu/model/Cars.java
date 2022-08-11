package com.rental.setu.model;

import com.rental.setu.constants.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents cars that are onboarded by the user and are
 * available to be rented out for trips.
 * A single car will be owned by a single user.
 * A single car can be a part of multiple, distinct rental trips.
 *
 * @author jimil
 */
@Entity
@Table(name = AppConstants.CARS_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cars implements Serializable {

    /**
     * Primary key identifying the car
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer car_id;

    /**
     * Indicates car is one of the type - SUV, MVP, SEDAN
     */
    @Column(nullable = false)
    private String type;

    /**
     * Availability zone wherein the car is available on rent
     */
    @Column(nullable = false)
    private String crz;

    /**
     * Foreign key to identify user that owns the car
     */
    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users owner;

    /**
     * Over-riden toString() method
     * @return this object's string representation
     */
    @Override
    public String toString() {
        return "Cars{" +
                "carId='" + car_id + '\'' +
                ", type='" + type + '\'' +
                ", crz='" + crz + '\'' +
                ", owner=" + owner +
                '}';
    }

}
