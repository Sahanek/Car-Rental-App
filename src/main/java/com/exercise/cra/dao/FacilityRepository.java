package com.exercise.cra.dao;

import com.exercise.cra.entities.Employee;
import com.exercise.cra.entities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query("Select f.employees from Facility f where f.id=:facilityId")
    List<Employee> getFacilityEmployees(long facilityId);
}
