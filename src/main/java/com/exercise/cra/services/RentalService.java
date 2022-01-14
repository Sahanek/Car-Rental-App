package com.exercise.cra.services;

import com.exercise.cra.entities.Rental;

import java.time.LocalDate;
import java.util.List;

public interface RentalService {

    Rental add(Rental rental);
    int getNumberOfRentedCarsBetween(LocalDate startDate, LocalDate endDate);
    List<Rental> getRentalsOfGivenCar(long carId);
}
