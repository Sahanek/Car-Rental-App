package com.exercise.cra.services;

import com.exercise.cra.entities.Employee;
import com.exercise.cra.utils.EmployeeSearchCriteria;

import java.util.List;

public interface EmployeeService {
    Employee add(Employee employee);

    Employee findById(long id);

    List<Employee> findByCriteria(EmployeeSearchCriteria employeeSearchCriteria);
}
