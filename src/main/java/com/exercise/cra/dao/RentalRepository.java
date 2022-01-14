package com.exercise.cra.dao;

import com.exercise.cra.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("Select count(r) from Rental r where r.rentStart >=?1 AND r.rentEnd <=?2")
    int getNumberOfRentedCarsBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Rental r WHERE r.rentCar.id=?1")
    List<Rental> getRentalsOfGivenCar(long carId);
}
