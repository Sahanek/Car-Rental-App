package com.exercise.cra.services;

import com.exercise.cra.entities.Employee;
import com.exercise.cra.entities.Facility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaAuditing
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:drop.sql"})
public class FacilityServiceImplTest {

    @Autowired
    FacilityServiceImpl facilityService;

    @Autowired
    EmployeeServiceImpl employeeService;


    @Test
    @Transactional
    public void shouldFindByIdWhenInDb() {
        //given
        Facility facility = getFacility();
        facilityService.add(facility);
        //when
        Facility result = facilityService.findById(facility.getId());
        //then
        assertEquals(facility, result);
    }

    @Test
    @Transactional
    public void shouldDeleteFromDb() {
        //given
        Facility facility = getFacility();
        facilityService.add(facility);
        List<Facility> facilitiesBeforeDelete = facilityService.findAll();
        //when
        facilityService.delete(facility);
        //then
        List<Facility> facilitiesAfterDelete = facilityService.findAll();
        assertTrue(facilitiesBeforeDelete.contains(facility));
        assertFalse(facilitiesAfterDelete.contains(facility));
    }

    @Test
    public void add() {
        //given
        Facility facility = getFacility();
        //when
        facilityService.add(facility);
        //then
        Facility facilityInDb = facilityService.findById(facility.getId());
        assertEquals(facility.getId(), facilityInDb.getId());
    }

    @Test
    public void shouldUpdateFacilityInDb() {
        //given
        Facility facility = getFacility();
        String newCity = "newCity";
        facilityService.add(facility);
        //when
        facility.setCity(newCity);
        facilityService.update(facility);
        //then
        Facility facilityInDb = facilityService.findById(facility.getId());
        assertEquals(newCity, facilityInDb.getCity());
    }

    @Test
    @Transactional
    public void assignEmployeeToFacility() {
        //given
        Facility facility = getFacility();
        facilityService.add(facility);
        Employee employee = Employee.builder().firstName("fN").lastName("lN").build();
        employeeService.add(employee);
        //when
        facilityService.assignEmployeeToFacility(employee, facility);
        //then
        Facility facilityInDb = facilityService.findById(facility.getId());
        assertTrue(facilityInDb.getEmployees().contains(employee));
        assertEquals(facilityInDb, employee.getFacility());
    }


    @Test
    public void shouldGetFacilityEmployees() {
        //given
        Facility facility = getFacility();
        facilityService.add(facility);
        Employee employee = Employee.builder().firstName("fN").lastName("lN").build();
        employeeService.add(employee);
        facilityService.assignEmployeeToFacility(employee, facility);
        //when
         List<Employee> employees =  facilityService.getFacilityEmployees(facility.getId());
        //then
        assertEquals(1, employees.size());
        assertEquals(employee.getId(), employees.get(0).getId());
    }

    @Test
    @Transactional
    public void deleteEmployeeFromFacility() {
        //given
        Facility facility = getFacility();
        facilityService.add(facility);
        Employee employee = Employee.builder().firstName("fN").lastName("lN").build();
        employeeService.add(employee);
        facilityService.assignEmployeeToFacility(employee, facility);
        //when
        facilityService.deleteEmployeeFromFacility(employee, facility);
        //then
        assertNull(employee.getFacility());
        assertFalse(facility.getEmployees().contains(employee));
    }

    private Facility getFacility() {
        return Facility.builder()
                .city("PL")
                .houseNumber("hn")
                .postCode("pC")
                .street("str").build();
    }
}