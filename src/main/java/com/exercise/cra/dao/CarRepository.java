package com.exercise.cra.dao;

import com.exercise.cra.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByTypeAndModel(String type, String model);

    @Query("SELECT c FROM Car c JOIN c.supervisors as s WHERE s.id = ?1")
    List<Car> findAllByEmployeeId(long employeeId);

    @Override
    List<Car> findAll();

    @Query("SELECT c FROM Car c where c.id IN (Select r.rentCar.id from Rental r Group By r.rentCar.id HAVING count(r.rentCar.id) > 10)")
    List<Car> getCarsRentedByMoreThan10People();

}
