package com.rental.setu.model;

import com.rental.setu.constants.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Represents a rental trip that is booked by a user.
 * A single user can book multiple rental trips.
 * A single car may be used for multiple rental trips.
 *
 * @author jimil
 */
@Entity
@Table(name = AppConstants.RENTALS_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rentals implements Serializable {

    /**
     * Primary key to identify this rental trip
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Foreign-key to identify the user that has booked this trip
     */
    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users customer;

    /**
     * Pickup and Drop locations are the same, hence tracked under
     * a single location field
     */
    @Column(nullable = false)
    private String location;

    /**
     * Starting date of the trip
     */
    @Column(nullable = false)
    private Date from_date;

    /**
     * Ending date of the trip
     */
    @Column(nullable = false)
    private Date to_date;

    /**
     * Total amount of the booking
     */
    @Column(nullable = false)
    private float amount;

    /**
     * Foreign key to identify the car that is booked for this trip
     */
    @ManyToOne()
    @JoinColumn(name = "car_id", referencedColumnName = "car_id", insertable = false, updatable = false)
    private Cars car_booked;

    /**
     * Overriden toString() method
     * @return this object's string representation
     */
    @Override
    public String toString() {
        return "Rentals{" +
                "id='" + id + '\'' +
                ", user=" + customer +
                ", location='" + location + '\'' +
                ", from_date=" + from_date.toString() +
                ", to_date=" + to_date.toString() +
                ", amount=" + amount +
                ", carId=" + car_booked +
                '}';
    }

}
