package com.rental.setu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDTO {

    @NotNull
    private Integer customer_id;
    @NotNull
    private String location;
    @NotNull
    private Date from_date;
    @NotNull
    private Date to_date;
    @NotNull
    private Integer car_id;

}
