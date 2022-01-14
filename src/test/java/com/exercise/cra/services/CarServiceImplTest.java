package com.exercise.cra.services;

import com.exercise.cra.entities.Car;
import com.exercise.cra.entities.Employee;
import com.exercise.cra.entities.Facility;
import com.exercise.cra.entities.Rental;
import com.exercise.cra.utils.EmployeeSearchCriteria;
import com.exercise.cra.utils.Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableJpaAuditing
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:drop.sql"})
public class CarServiceImplTest {

    @Autowired
    CarServiceImpl carService;

    @Autowired
    EmployeeServiceImpl empService;

    @Autowired
    FacilityServiceImpl facilityService;

    @Autowired
    RentalServiceImpl rentalService;

    @Test
    public void shouldThrowOptimisticLockingEx() {
        //given
        Car car = getCar("type", "m");
        carService.add(car);
        //when
        Car car1 = carService.findById(car.getId());
        Car car2 = carService.findById(car.getId());
        car2.setType("t2");
        carService.update(car2);
        car1.setType("t1");
        //then
        assertThrows(OptimisticLockingFailureException.class, () -> carService.update(car1));
    }

    @Test
    public void shouldDeleteCar() {
        //given
        Car car = getCar("type", "model");
        carService.add(car);
        int sizeBeforeDeleting = carService.findAll().size();
        //when
        carService.delete(car);
        //then
        int carsAfterDeleting = carService.findAll().size();
        assertEquals(sizeBeforeDeleting - 1, carsAfterDeleting);
    }

    @Test
    public void shouldAddCar() {
        //given
        Car car = getCar("type", "model");
        int carsBeforeAddingNew = carService.findAll().size();
        //when
        carService.add(car);
        //then
        int carsAfterAddingNew = carService.findAll().size();
        assertEquals(carsBeforeAddingNew + 1, carsAfterAddingNew);
    }

    @Test
    public void shouldUpdateCar() {
        //given
        Car car = getCar("type", "modelFirst");
        Car addedCar = carService.add(car);
        String newType = "newType";
        //when
        car.setType(newType);
        Car updatedCar = carService.update(car);
        //then
        Car updatedCarInDb = carService.findById(addedCar.getId());
        assertEquals(updatedCarInDb.getType(), newType);
    }

    @Test
    public void shouldFindByTypeAndModel() {
        //given
        String type = "findByType";
        String model = "findByModel";
        Car car = getCar(type, model);
        Car addedCar = carService.add(car);
        //when
        List<Car> cars = carService.findByTypeAndModel(type, model);
        //then
        assertEquals(1, cars.size());
        assertEquals(addedCar.getId(), cars.get(0).getId());
    }

    @Test
    @Transactional
    public void assignCarToEmployee() {
        //given
        Employee employee = getEmployee("fn", "ln", "999", Position.VENDOR);
        empService.add(employee);
        Car car = getCar("t", "m");
        carService.add(car);
        //when
        carService.assignCarToEmployee(car.getId(), employee);
        //then
        Car carFromDb = carService.findById(car.getId());
        assertTrue(carFromDb.getSupervisors().contains(employee));
    }

    @Test
    public void shouldFindByEmployeeId() {
        //given
        Employee employee = getEmployee("first", "last", "99", Position.ACCOUNTANT);
        empService.add(employee);
        Car carWithSupervisor = getCar("type", "model");
        carService.add(carWithSupervisor);
        carService.assignCarToEmployee(carWithSupervisor.getId(), employee);
        Car carWithoutSupervisors = getCar("t", "m");
        carService.add(carWithoutSupervisors);
        //when
        List<Car> cars = carService.findAllByEmployeeId(employee.getId());
        //then
        assertEquals(1, cars.size());
        assertEquals(carWithSupervisor.getId(), cars.get(0).getId());
    }

    @Test
    @Transactional
    public void shouldFindByCriteria() {
        //given
        Employee employee = getEmployee("first", "last", "99", Position.ACCOUNTANT);
        empService.add(employee);
        Employee employee2 = getEmployee("fne2", "lne2", "99", Position.VENDOR);
        empService.add(employee2);
        Car car = getCar("t", "m");
        carService.add(car);
        carService.assignCarToEmployee(car.getId(), employee);
        carService.assignCarToEmployee(car.getId(), employee2);
        Facility facility = Facility.builder().city("city").build();
        facilityService.add(facility);
        facilityService.assignEmployeeToFacility(employee, facility);
        //when
        List<Employee> employeesWithAssignedCarAndFacility = empService.findByCriteria(
                EmployeeSearchCriteria.builder().carId(car.getId()).facilityId(facility.getId()).build());

        List<Employee> employeesWithAssignedCarPositionAccountantAndFacility = empService.findByCriteria(
                EmployeeSearchCriteria.builder().carId(car.getId()).position(Position.ACCOUNTANT).facilityId(facility.getId()).build());

        List<Employee> employeesWithAssignedCar = empService.findByCriteria(
                EmployeeSearchCriteria.builder().carId(car.getId()).build());
        //then
        assertEquals(employee.getId(), employeesWithAssignedCarAndFacility.get(0).getId());
        assertEquals(employee.getId(), employeesWithAssignedCarPositionAccountantAndFacility.get(0).getId());
        assertEquals(List.of(employee, employee2), employeesWithAssignedCar);
    }

    @Test
    public void shouldGetCarsRentedByMoreThan10PeopleReturnCorrectData() {
        //given
        Car car = carService.add(getCar("tx", "m3"));
        for (int i = 0; i <= 10; i++) {
            rentalService.add(Rental.builder().rentCar(car).build());
        }
        //when
        List<Car> cars = carService.getCarsRentedByMoreThan10People();
        //then
        assertEquals(1, cars.size());
        assertEquals(car.getId(), cars.get(0).getId());

    }

    @Test
    public void shouldDeleteRentalAssociatedWithCarWhenDeleteCar(){
        String toDelete = "toDelete";
        Car car = carService.add(getCar(toDelete, toDelete));
        for (int i = 0; i <= 10; i++) {
            rentalService.add(Rental.builder().rentCar(car).build());
        }
        //when
        List<Rental> rentalsBeforeDeleted = rentalService.getRentalsOfGivenCar(car.getId());
        carService.delete(car);
        //then
        List<Rental> rentalsAfterDeleted = rentalService.getRentalsOfGivenCar(car.getId());
        assertEquals(11, rentalsBeforeDeleted.size());
        assertEquals(0, rentalsAfterDeleted.size());
    }

    private Car getCar(String type, String model) {
        return Car.builder().type(type).model(model).color("red").engineCapacity("1.5").mileage("100km").productionDate(LocalDate.of(1998, 1, 1)).build();
    }

    private Employee getEmployee(String first, String last, String phone, Position accountant) {
        return Employee.builder().firstName(first).lastName(last).phoneNumber(phone).position(accountant).build();
    }
}