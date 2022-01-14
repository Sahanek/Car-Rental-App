package com.exercise.cra.services;

import com.exercise.cra.dao.CarRepository;
import com.exercise.cra.entities.Car;
import com.exercise.cra.entities.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepo;

    @Override
    @Transactional
    public void delete(Car car) {
        carRepo.delete(car);
    }

    @Transactional
    @Override
    public Car add(Car car) {
        return carRepo.save(car);
    }

    @Override
    public Car update(Car car) {
        return carRepo.save(car);
    }

    @Override
    public Car findById(long id) {
        return carRepo.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    @Transactional
    @Override
    public Car assignCarToEmployee(long carId, Employee employee) {
        Car car = findById(carId);
        if (car.getSupervisors() == null) {
            car.setSupervisors(new ArrayList<>());
        }
        car.getSupervisors().add(employee);
        return carRepo.saveAndFlush(car);
    }

    @Override
    public List<Car> findByTypeAndModel(String type, String model) {
        return carRepo.findAllByTypeAndModel(type, model);
    }

    @Override
    public List<Car> findAllByEmployeeId(long id) {
        return carRepo.findAllByEmployeeId(id);
    }

    @Override
    public List<Car> findAll() {
        return carRepo.findAll();
    }

    @Override
    public List<Car> getCarsRentedByMoreThan10People() {
        return carRepo.getCarsRentedByMoreThan10People();
    }
}
