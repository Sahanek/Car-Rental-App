package com.exercise.cra.services;

import com.exercise.cra.dao.FacilityRepository;
import com.exercise.cra.entities.Employee;
import com.exercise.cra.entities.Facility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepo;

    @Override
    public Facility findById(long id) {
        return facilityRepo.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    @Override
    public List<Facility> findAll() {
        return facilityRepo.findAll();
    }

    @Override
    public void delete(Facility facility) {
        facilityRepo.delete(facility);
    }

    @Override
    @Transactional
    public Facility add(Facility facility) {
        return facilityRepo.save(facility);
    }

    @Override
    public Facility update(Facility facility) {
        return facilityRepo.save(facility);
    }

    @Override
    @Transactional
    public Facility assignEmployeeToFacility(Employee employee, Facility facility) {
        employee.setFacility(facility);
        if (facility.getEmployees() == null) {
            facility.setEmployees(new ArrayList<>());
        }
        facility.getEmployees().add(employee);
        return facilityRepo.saveAndFlush(facility);
    }

    @Override
    @Transactional
    public void deleteEmployeeFromFacility(Employee employee, Facility facility) {
        if (!facility.getEmployees().remove(employee)) {
            throw new RuntimeException("Facility doesn't have that employee." + employee);
        }
        employee.setFacility(null);
        facilityRepo.saveAndFlush(facility);
    }

    @Override
    @Transactional
    public List<Employee> getFacilityEmployees(long facilityId){
        return facilityRepo.getFacilityEmployees(facilityId);
    }
}
