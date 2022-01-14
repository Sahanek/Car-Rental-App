package com.exercise.cra.services;

import com.exercise.cra.entities.Car;
import com.exercise.cra.entities.Employee;

import java.util.List;

public interface CarService {
    void delete(Car car);

    Car add(Car car);

    Car update(Car car);

    Car findById(long id);

    Car assignCarToEmployee(long carId, Employee employee);

    List<Car> findByTypeAndModel(String type, String model);

    List<Car> findAllByEmployeeId(long id);

    List<Car> findAll();

    List<Car> getCarsRentedByMoreThan10People();
}
