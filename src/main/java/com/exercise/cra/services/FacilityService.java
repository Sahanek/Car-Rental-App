package com.exercise.cra.services;

import com.exercise.cra.entities.Employee;
import com.exercise.cra.entities.Facility;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FacilityService {
    Facility findById(long id);

    List<Facility> findAll();

    void delete(Facility facility);

    Facility add(Facility facility);

    Facility update(Facility facility);

    @Transactional
    Facility assignEmployeeToFacility(Employee employee, Facility facility);

    @Transactional
    void deleteEmployeeFromFacility(Employee employee, Facility facility);

    @Transactional
    List<Employee> getFacilityEmployees(long facilityId);
}
