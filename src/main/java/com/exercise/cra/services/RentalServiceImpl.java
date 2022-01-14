package com.exercise.cra.services;

import com.exercise.cra.dao.RentalRepository;
import com.exercise.cra.entities.Rental;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepo;

    public Rental add(Rental rental) {
        return rentalRepo.save(rental);
    }

    public int getNumberOfRentedCarsBetween(LocalDate startDate, LocalDate endDate) {
        return rentalRepo.getNumberOfRentedCarsBetween(startDate,endDate);
    }

    public List<Rental> getRentalsOfGivenCar(long carId){
        return rentalRepo.getRentalsOfGivenCar(carId);
    }
}
