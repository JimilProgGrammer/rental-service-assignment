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
 * Represents users who can onboard or book cars on rent
 * via the renting platform.
 * A user can own multiple cars.
 * A user may or may not book single or multiple rental trips.
 *
 * @author jimil
 */
@Entity
@Table(name = AppConstants.USERS_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {

    /**
     * Primary key identifying the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    /**
     * Unique user email
     */
    @Column(nullable = false)
    private String useremail;

    /**
     * Contact Details
     */
    @Column(nullable = false)
    private String phone;

    /**
     * Date of birth
     */
    @Column(nullable = false)
    private Date dob;

    /**
     * Over-riden toString() method
     * @return this object's string representation
     */
    @Override
    public String toString() {
        return "Users{" +
                "userId=" + user_id +
                ", useremail='" + useremail + '\'' +
                ", phone='" + phone + '\'' +
                ", dob=" + dob.toString() +
                '}';
    }

}
